package com.mide.gangsaeng.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardReadServiceCacheImpl implements BoardReadService {
    private final RedisCommands<String, String> redisCommands;
    private final ObjectMapper objectMapper;

    @Autowired
    public BoardReadServiceCacheImpl(RedisCommands<String, String> redisCommands, ObjectMapper objectMapper) {
        this.redisCommands = redisCommands;
        this.objectMapper = objectMapper;
    }

    @Override
    public BoardResponse read(long id) {
        Board board = fetchCachedBoard(id);

        if (board == null) {
            return null;
        }

        return BoardResponse.builder()
                            .id(board.getId())
                            .title(board.getTitle())
                            .content(board.getContent())
                            .userId(board.getUserId())
                            .createdAt(board.getCreatedAt())
                            .updatedAt(board.getUpdatedAt())
                            .build();
    }

    private Board fetchCachedBoard(long id) {
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
