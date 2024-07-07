package com.mide.gangsaeng.common.cursor;

import java.util.List;

import lombok.Getter;

@Getter
public class CursorBasedResponse<T> {
    private final List<T> data;
    private final Cursor cursor;

    public CursorBasedResponse(List<T> data, Cursor cursor) {
        this.data = data;
        this.cursor = cursor;
    }
}
