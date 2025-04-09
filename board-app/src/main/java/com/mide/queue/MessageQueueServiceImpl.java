package com.mide.queue;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.protobuf.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageQueueServiceImpl implements MessageQueueService {
    private final SqsClient sqsClient;

    @Value("${aws.sqs.board-queue-url}")
    private String queueUrl;
    @Override
    public void send(Message message) {
        try {
            byte[] serialized = message.toByteArray();

            String encodedMessage = Base64.getEncoder().encodeToString(serialized);

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(encodedMessage)
                    .build();

            sqsClient.sendMessage(request);
            log.info("send message to SQS : {}", message);

        } catch (Exception e) {
            log.error("send message to SQS fail : {}", e.getMessage());
        }

    }
}
