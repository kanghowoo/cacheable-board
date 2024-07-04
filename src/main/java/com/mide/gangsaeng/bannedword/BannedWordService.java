package com.mide.gangsaeng.bannedword;

import java.util.List;

public interface BannedWordService {
    boolean added(String word);
    boolean delete(String word);
    List<BannedWord> list();

    boolean findBannedWords(String content);
}
