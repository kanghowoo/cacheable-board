package com.mide.gangsaeng.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CursorBasedRequest {
    private final Long cursor;
    private final String direction;
    private final int size;

    public CursorBasedRequest(Long cursor, String direction, int size) {
        this.cursor = setCursor(cursor);
        this.direction = direction;
        this.size = size;
    }

    private long setCursor(Long cursor) {
        if (cursor == null) {
            return Long.MAX_VALUE;
        }
        return cursor;
    }

}
