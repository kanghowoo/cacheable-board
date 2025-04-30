package com.mide.message.handler;

import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;

import software.amazon.awssdk.services.sqs.model.Message;

public interface MessageHandler {
    boolean supports(BoardFailedMessageWrapper wrapper);
    void handle(BoardFailedMessageWrapper wrapper, Message message);
}
