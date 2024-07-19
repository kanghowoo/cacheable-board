package com.mide.gangsaeng.board;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.lettuce.core.RedisException;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BoardCacheRepositoryImpl implements BoardRepository {
    private final RedisCommands<String, String> redisCommands;
    private final ObjectMapper objectMapper;

    @Autowired
    public BoardCacheRepositoryImpl(RedisCommands<String, String> redisCommands, ObjectMapper objectMapper) {
        this.redisCommands = redisCommands;
        this.objectMapper = objectMapper;
    }

    @Override
    public void write(Board board) {

        try {
            String boardValue = objectMapper.writeValueAsString(board);
            final long boardId = board.getId();
            redisCommands.set(makeBoardKey(boardId), boardValue, boardCachedDuration());
        } catch (JsonProcessingException | RedisException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(Board board) {
        this.write(board);
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

    @Override
    public List<Board> getPage(int offset, int size) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public List<Board> getPrevPage(long cursor, int size) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public List<Board> getNextPage(long cursor, int size) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    private SetArgs boardCachedDuration() {
        return SetArgs.Builder.ex(Duration.ofMinutes(30));
    }

    private String makeBoardKey(long id) {
        return "board:" + id;
    }
}
