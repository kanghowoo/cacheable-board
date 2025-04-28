package com.mide.queue;

import com.google.protobuf.Message;

public class ProtobufQueueMessage<T extends Message> implements QueueMessage<T> {

    private final T message;

    public ProtobufQueueMessage(T message) {
        this.message = message;
    }

    @Override
    public byte[] serialize() {
        return message.toByteArray();
    }

    @Override
    public T getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message.toString();
    }
}
