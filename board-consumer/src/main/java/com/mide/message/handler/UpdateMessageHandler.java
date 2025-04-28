package com.mide.message.handler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mide.board.BoardProtoMapper;
import com.mide.board.BoardRepository;
import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;

@Slf4j
@Component
public class UpdateMessageHandler implements MessageHandler {

    private final BoardRepository boardRepository;
    private final BoardProtoMapper boardProtoMapper;
    private final SqsClient sqsClient;

    @Value("${aws.sqs.board-queue-url}")
    private String queueUrl;

    public UpdateMessageHandler(@Qualifier("retryableBoardRepository") BoardRepository boardRepository,
                                BoardProtoMapper boardProtoMapper,
                                SqsClient sqsClient) {
        this.boardRepository = boardRepository;
        this.boardProtoMapper = boardProtoMapper;
        this.sqsClient = sqsClient;
    }

    @Override
    public boolean supports(BoardFailedMessageWrapper wrapper) {
        return wrapper.hasUpdate();
    }

    @Override
    public void handle(BoardFailedMessageWrapper wrapper, Message message) {
        try {
            boardRepository.update(boardProtoMapper.toEntity(wrapper.getUpdate()));
            deleteMessage(message);
        } catch (Exception e) {
            log.error("create handler failed", e);
        }
    }

    private void deleteMessage(Message message) {
        sqsClient.deleteMessage(DeleteMessageRequest.builder()
                                                    .queueUrl(queueUrl)
                                                    .receiptHandle(message.receiptHandle())
                                                    .build());
    }
}
