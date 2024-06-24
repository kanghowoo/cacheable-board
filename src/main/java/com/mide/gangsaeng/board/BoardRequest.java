package com.mide.gangsaeng.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardRequest {
    private String title;
    private String content;
    private int userId;
}
