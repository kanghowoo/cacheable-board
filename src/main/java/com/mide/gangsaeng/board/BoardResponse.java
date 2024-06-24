package com.mide.gangsaeng.board;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponse {
    private String title;
    private String content;
    private int userId;
    private LocalDateTime updatedAt;

}
