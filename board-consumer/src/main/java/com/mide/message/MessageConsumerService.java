package com.mide.message;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.protobuf.InvalidProtocolBufferException;

import com.mide.board.BoardProtoMapper;
import com.mide.board.BoardRepository;
import com.mide.gangsaeng.BoardProto.BoardCreateFailedMessage;
import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;
import com.mide.gangsaeng.BoardProto.BoardUpdateFailedMessage;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

@Slf4j
@Service
public class MessageConsumerService {

    private final SqsClient sqsClient;
    private final BoardRepository boardRepository;
    private final BoardProtoMapper boardProtoMapper;

    @Value("${aws.sqs.board-queue-url}")
    private String queueUrl;

    public MessageConsumerService(SqsClient sqsClient,
                                  @Qualifier("retryableBoardRepository") BoardRepository boardRepository,
                                  BoardProtoMapper boardProtoMapper) {
        this.sqsClient = sqsClient;
        this.boardRepository = boardRepository;
        this.boardProtoMapper = boardProtoMapper;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void consumeAndProcess() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(20)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();

        log.info("message size : {}" , messages.size());

        messages.forEach(
                message -> {
                    log.info("polled message");
                    try {
                        byte[] deserialized = Base64.getDecoder().decode(message.body());

                        BoardFailedMessageWrapper wrapper =
                                BoardFailedMessageWrapper.parseFrom(deserialized);

                        if (wrapper.hasCreate()) {
                            BoardCreateFailedMessage createFailedMessage = wrapper.getCreate();

                            boardRepository.write(boardProtoMapper.toEntity(createFailedMessage));
                            deleteMessage(message);

                        } else if (wrapper.hasUpdate()) {
                            BoardUpdateFailedMessage updateFailedMessage = wrapper.getUpdate();

                            boardRepository.update(boardProtoMapper.toEntity(updateFailedMessage));
                            deleteMessage(message);

                        }

                    } catch (IllegalArgumentException | InvalidProtocolBufferException e) {
                        log.error("decoding or parsing failed. : {}", message.body());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }

                    log.info("retry success");
                }
        );
    }

    private void deleteMessage(Message message) {
        sqsClient.deleteMessage(DeleteMessageRequest.builder()
                                                    .queueUrl(queueUrl)
                                                    .receiptHandle(message.receiptHandle())
                                                    .build());
    }

}
