package com.mide.gangsaeng.bannedword;

import java.util.Objects;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BannedWord {
    private final String word;

    public BannedWord(String word) {
        validate(word);
        this.word = wordToLowerCase(word);
    }

    private String wordToLowerCase(String word) {
        return word.toLowerCase();
    }

    private void validate(String word) {
        if (word == null || word.isBlank()) {
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
