package com.mide.gangsaeng.common.cursor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Cursor {
    private Long prevCursor;
    private Long nextCursor;

    public Cursor(Long prevCursor, Long nextCursor) {
        this.prevCursor = prevCursor;
        this.nextCursor = nextCursor;
    }

}
