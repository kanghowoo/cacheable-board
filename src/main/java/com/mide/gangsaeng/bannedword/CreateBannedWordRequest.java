package com.mide.gangsaeng.bannedword;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBannedWordRequest {
    @NotBlank
    private final String word;

    @JsonCreator
    public CreateBannedWordRequest(String word) {
        this.word = word;
    }
}
