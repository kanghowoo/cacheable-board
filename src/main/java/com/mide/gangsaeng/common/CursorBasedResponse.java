package com.mide.gangsaeng.common;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CursorBasedResponse<T> {
    private final List<T> data;
    private final Cursor cursor;

    public CursorBasedResponse(List<T> data, Cursor cursor) {
        this.data = data;
        this.cursor = cursor;
    }
}
