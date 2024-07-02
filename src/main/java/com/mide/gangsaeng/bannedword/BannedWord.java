package com.mide.gangsaeng.bannedword;

import java.util.Objects;

import lombok.Getter;

@Getter
public class BannedWord {
    private final String word;

    public BannedWord(String word) {
        validate(word);
        this.word = word;
    }

    private void validate(String word) {
        if (word == null) {
            throw new IllegalArgumentException("null parameter is not allowed in banned word");
        }

        if (word.isBlank() || word.isEmpty()) {
            throw new IllegalArgumentException("empty string is not allowed in banned word");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        BannedWord that = (BannedWord) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}
