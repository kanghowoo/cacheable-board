package com.mide.gangsaeng.bannedword;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class BannedWordServiceImpl implements BannedWordService {

    private static ConcurrentHashMap<String,BannedWord> cache;

    public BannedWordServiceImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void add(String word) {
        if (isBanned(word)) {
            throw new IllegalArgumentException("Already added.");
        }

        cache.put(word, new BannedWord(word));
    }

    @Override
    public void delete(String word) {
        if (!isBanned(word)) {
            throw new IllegalArgumentException("Not added.");
        }

        cache.remove(word);
    }

    @Override
    public List<BannedWord> list() {
        return cache.values().stream().toList();
    }

    private static boolean isBanned(String word) {
        return cache.containsKey(word);
    }

}
