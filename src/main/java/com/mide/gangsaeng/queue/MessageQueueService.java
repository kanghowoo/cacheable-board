package com.mide.gangsaeng.queue;

import org.springframework.stereotype.Service;

@Service
public interface MessageQueueService {
    void send(QueueMessage message);
}
