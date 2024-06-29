package com.mide.gangsaeng.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Cursor {
    private long prevCursor;
    private long nextCursor;

    public Cursor(long prevCursor, long nextCursor) {
        this.prevCursor = prevCursor;
        this.nextCursor = nextCursor;
    }

}
