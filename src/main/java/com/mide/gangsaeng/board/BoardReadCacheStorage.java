package com.mide.gangsaeng.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BoardReadCacheStorage implements BoardReadStorage {
    private final RedisCommands<String, String> redisCommands;
    private final ObjectMapper objectMapper;

    @Autowired
    public BoardReadCacheStorage(RedisCommands<String, String> redisCommands, ObjectMapper objectMapper) {
        this.redisCommands = redisCommands;
        this.objectMapper = objectMapper;
    }

    @Override
    public Board read(long id) {
        String boardRedisKey = makeBoardKey(id);
        String boardValue;

        try {
            boardValue = redisCommands.get(boardRedisKey);
        } catch (RedisException e) {
            log.error(e.getMessage(), e);
            return null;
        }

        if (boardValue == null) {
            return null;
        }

        try {
            return objectMapper.readValue(boardValue, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private String makeBoardKey(long id) {
        return "board:" + id;
    }
}
