package com.mide.gangsaeng.board;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.lettuce.core.RedisException;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CacheableBoardReadStorage implements BoardReadStorage {
    private final BoardReadStorage db;
    private final BoardReadStorage cache;
    private final RedisCommands<String, String> redisCommands;
    private final ObjectMapper objectMapper;

    @Autowired
    public CacheableBoardReadStorage(
            @Qualifier("boardReadRdbStorage") BoardReadStorage db,
            @Qualifier("boardReadCacheStorage") BoardReadStorage cache,
            RedisCommands<String, String> redisCommands,
            ObjectMapper objectMapper) {
        this.db = db;
        this.cache = cache;
        this.redisCommands = redisCommands;
        this.objectMapper = objectMapper;
    }

    @Override
    public Board read(long id) {
        Board board = cache.read(id);

        if (board == null) {
            board = db.read(id);
            cacheBoard(board);
        }

        return board;
    }

    public void cacheBoard(Board board) {

        try {
            String boardValue = objectMapper.writeValueAsString(board);
            final long boardId = board.getId();
            redisCommands.set(makeBoardKey(boardId), boardValue, boardCachedDuration());
        } catch (JsonProcessingException | RedisException e) {
            log.error(e.getMessage(), e);
        }
    }

    private SetArgs boardCachedDuration() {
        return SetArgs.Builder.ex(Duration.ofMillis(10_000));
    }

    private String makeBoardKey(long id) {
        return "board:" + id;
    }
}
