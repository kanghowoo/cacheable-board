package com.mide.gangsaeng.board;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Board {
    private final int id;
    private final String title;
    private final String content;
    private final int userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
