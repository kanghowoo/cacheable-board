package com.mide.gangsaeng.bannedword;

import java.util.List;

import lombok.Getter;

@Getter
public class BannedWordResponse<T> {
    private final List<T> data;

    public BannedWordResponse(List<T> words) {
        this.data = words;
    }

}
