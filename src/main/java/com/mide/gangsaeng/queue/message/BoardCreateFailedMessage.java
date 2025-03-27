package com.mide.gangsaeng.queue.message;

import java.time.LocalDateTime;

import com.mide.gangsaeng.board.Board;
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
    private final long userId;
    private final LocalDateTime createdAt;

    public BoardCreateFailedMessage(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userId = board.getUserId();
        this.createdAt = board.getCreatedAt();
    }
}
