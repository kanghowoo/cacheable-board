package com.mide.queue;

public interface QueueMessage<T> {
    byte[] serialize();
    T getMessage();
}
