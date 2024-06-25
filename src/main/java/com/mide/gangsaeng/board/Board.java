package com.mide.gangsaeng.board;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Board {
    private final long id;
    private final String title;
    private final String content;
    private final long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
