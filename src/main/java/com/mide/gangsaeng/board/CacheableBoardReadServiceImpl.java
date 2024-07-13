package com.mide.gangsaeng.board;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.lettuce.core.RedisException;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CacheableBoardReadServiceImpl implements BoardReadService {

    private final BoardReadService db;
    private final BoardReadService cache;
    private final RedisCommands<String, String> redisCommands;
    private final ObjectMapper objectMapper;

    @Autowired
    public CacheableBoardReadServiceImpl(
            @Qualifier("boardReadServiceImpl") BoardReadService db,
            @Qualifier("boardReadServiceCacheImpl") BoardReadService cache,
            RedisCommands<String, String> redisCommands,
            ObjectMapper objectMapper) {
        this.db = db;
        this.cache = cache;
        this.redisCommands = redisCommands;
        this.objectMapper = objectMapper;
    }

    @Override
    public BoardResponse read(long id) {
        BoardResponse boardResponse = cache.read(id);

        if (boardResponse == null) {
            boardResponse = db.read(id);
            cacheBoard(boardResponse);

            return boardResponse;
        }

        return boardResponse;
    }

    public void cacheBoard(BoardResponse boardResponse) {

        try {
            String boardValue = objectMapper.writeValueAsString(boardResponse);
            final long boardId = boardResponse.getId();
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
