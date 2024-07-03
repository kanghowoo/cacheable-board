package com.mide.gangsaeng.bannedword;

import java.util.List;

public interface BannedWordService {
    String add(String word);
    String delete(String word);
    List<BannedWord> list();
}
