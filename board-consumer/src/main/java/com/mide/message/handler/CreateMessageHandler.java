package com.mide.message.handler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mide.board.BoardProtoMapper;
import com.mide.board.BoardRepository;
import com.mide.gangsaeng.BoardProto.BoardCreateFailedMessage;
import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;
import com.mide.queue.MessageQueueService;
import com.mide.queue.ProtobufQueueMessage;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;

@Slf4j
@Component
public class CreateMessageHandler implements MessageHandler {

    private final BoardRepository boardRepository;
    private final BoardProtoMapper boardProtoMapper;
    private final SqsClient sqsClient;
    private final MessageQueueService messageQueueService;

    @Value("${aws.sqs.board-queue-url}")
    private String queueUrl;

    public CreateMessageHandler(@Qualifier("boardRdbRepositoryImpl") BoardRepository boardRepository,
                                BoardProtoMapper boardProtoMapper,
                                SqsClient sqsClient,
                                MessageQueueService messageQueueService) {
        this.boardRepository = boardRepository;
        this.boardProtoMapper = boardProtoMapper;
        this.sqsClient = sqsClient;
        this.messageQueueService = messageQueueService;
    }

    @Override
    public boolean supports(BoardFailedMessageWrapper wrapper) {
        return wrapper.hasCreate();
    }

    @Override
    public void handle(BoardFailedMessageWrapper wrapper, Message message) {
        BoardCreateFailedMessage original = wrapper.getCreate();

        try {
            boardRepository.write(boardProtoMapper.toEntity(wrapper.getCreate()));
            deleteMessage(message);
        } catch (Exception e) {
            log.error("create handler failed", e);

            int retryCount = original.getRetryCount() + 1;
            log.info("RetryCount : {}", retryCount);

            if (retryCount > 5) {
                log.warn("Retry count exceeded. Dropping message.");
                deleteMessage(message);
                return;
            }

            BoardCreateFailedMessage retried = original.toBuilder()
                                                       .setRetryCount(retryCount)
                                                       .build();

            BoardFailedMessageWrapper retryWrapper = BoardFailedMessageWrapper.newBuilder()
                                                                              .setCreate(retried)
                                                                              .build();

            messageQueueService.send(new ProtobufQueueMessage(retryWrapper)); // 재전송
            deleteMessage(message); // 기존 메시지는 삭제
        }
    }

    private void deleteMessage(Message message) {
        sqsClient.deleteMessage(DeleteMessageRequest.builder()
                                                    .queueUrl(queueUrl)
                                                    .receiptHandle(message.receiptHandle())
                                                    .build());
    }
}
