package com.mide.gangsaeng.bannedword;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class BannedWordServiceImpl implements BannedWordService {

    private static final ConcurrentHashMap<String,BannedWord> cache = new ConcurrentHashMap<>();

    @Override
    public String add(String word) {
        if (isBanned(word)) {
            return "Already added.";
        }

        cache.put(word, new BannedWord(word));

        return "success";
    }

    @Override
    public String delete(String word) {
        if (!isBanned(word)) {
            return "Not added.";
        }

        cache.remove(word);

        return "success";
    }

    @Override
    public List<BannedWord> list() {
        return cache.values().stream().toList();
    }

    private static boolean isBanned(String word) {
        return cache.containsKey(word);
    }

}
