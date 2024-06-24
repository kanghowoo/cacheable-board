package com.mide.gangsaeng.board;

import org.springframework.stereotype.Repository;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardMapper mapper;

    public BoardRepositoryImpl(BoardMapper mapper) {this.mapper = mapper;}

    public void write(BoardRequest request) {
        mapper.write(request);
    }
}
