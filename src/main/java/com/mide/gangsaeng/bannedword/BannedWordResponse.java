package com.mide.gangsaeng.bannedword;

import java.util.List;

import lombok.Getter;

@Getter
public class BannedWordResponse {
    private final List<BannedWord> data;

    public BannedWordResponse(List<BannedWord> words) {
        this.data = words;
    }

}
