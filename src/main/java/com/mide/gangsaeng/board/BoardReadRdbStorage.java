package com.mide.gangsaeng.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardReadRdbStorage implements BoardReadStorage {
    private final BoardMapper mapper;

    @Autowired
    public BoardReadRdbStorage(BoardMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Board read(long id) {
        return mapper.read(id);
    }
}
