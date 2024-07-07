package com.mide.gangsaeng.bannedword;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.mide.gangsaeng.common.error.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BannedWordServiceImpl implements BannedWordService {

    private static final ConcurrentHashMap<String,BannedWord> cache = new ConcurrentHashMap<>();

    @Override
    public boolean add(String word) {
        BannedWord previousValue = cache.putIfAbsent(word, new BannedWord(word));

        return previousValue == null;
    }

    @Override
    public boolean delete(String word) {
        if (!isBanned(word)) {
            return false;
        }

        cache.remove(word);
        return true;
    }

    @Override
    public List<BannedWord> list() {
        return cache.values().stream().toList();
    }

    @Override
    public void validateBannedWords(String content) {
        String target = content.toLowerCase();

        List<BannedWord> wordList = new ArrayList<>();

        cache.values().forEach(bannedWord -> {
            if (target.contains(bannedWord.getWord())) {
                wordList.add(bannedWord);
            }
        });

        if (!wordList.isEmpty()) {
            log.info("include banned words : {}", wordList);
            throw new IncludeBannedWordException(ErrorCode.INCLUDE_BANNED_WORD);
        }

    }

    private static boolean isBanned(String word) {
        return cache.containsKey(word);
    }

}
