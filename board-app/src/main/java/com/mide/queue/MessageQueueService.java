package com.mide.queue;

import org.springframework.stereotype.Service;

import com.google.protobuf.Message;

@Service
public interface MessageQueueService {
    <T> void send(QueueMessage<T> message);
}
