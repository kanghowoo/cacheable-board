package com.mide.gangsaeng.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
