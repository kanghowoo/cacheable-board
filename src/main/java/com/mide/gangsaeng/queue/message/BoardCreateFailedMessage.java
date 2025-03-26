package com.mide.gangsaeng.queue.message;

import com.mide.gangsaeng.queue.QueueMessage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardCreateFailedMessage implements QueueMessage {
    private final String title;
    private final String content;
}
