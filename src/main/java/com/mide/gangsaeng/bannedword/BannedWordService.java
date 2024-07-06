package com.mide.gangsaeng.bannedword;

import java.util.List;

public interface BannedWordService {
    boolean add(String word);
    boolean delete(String word);
    List<BannedWord> list();

    void validateBannedWords(String content);
}
