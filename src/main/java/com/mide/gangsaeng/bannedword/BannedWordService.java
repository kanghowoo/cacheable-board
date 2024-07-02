package com.mide.gangsaeng.bannedword;

import java.util.List;

public interface BannedWordService {
    void add(String word);
    void delete(String word);
    List<BannedWord> list();
}
