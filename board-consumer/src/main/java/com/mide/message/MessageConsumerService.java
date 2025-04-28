package com.mide.message;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;
import com.mide.message.handler.MessageHandler;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

@Slf4j
@Service
public class MessageConsumerService {

    private final SqsClient sqsClient;
    private final List<MessageHandler> messageHandlers;

    @Value("${aws.sqs.board-queue-url}")
    private String queueUrl;

    public MessageConsumerService(SqsClient sqsClient,
                                  List<MessageHandler> messageHandlers) {
        this.sqsClient = sqsClient;
        this.messageHandlers = messageHandlers;

    }

    @Async("consumingExecutor")
    public void consumeAndProcess() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(20)
                .build();

        while (true) {
            List<Message> messages = sqsClient.receiveMessage(request).messages();
            log.info("message size : {}" , messages.size());

            for (Message message : messages) {
                try {
                    byte[] deserialized = Base64.getDecoder().decode(message.body());

                    BoardFailedMessageWrapper wrapper =
                            BoardFailedMessageWrapper.parseFrom(deserialized);

                    messageHandlers.stream()
                                   .filter(handler -> handler.supports(wrapper))
                                   .findFirst()
                                   .ifPresent(handler -> handler.handle(wrapper, message));

                } catch (Exception e) {
                    log.error("Message handling failed", e);
                }
            }

        }

    }

}
