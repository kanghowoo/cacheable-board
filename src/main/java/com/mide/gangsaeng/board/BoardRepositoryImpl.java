package com.mide.gangsaeng.board;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardMapper mapper;

    public BoardRepositoryImpl(BoardMapper mapper) {this.mapper = mapper;}

    public void write(BoardRequest request) {
        mapper.write(request);
    }

    @Override
    public Board read(long id) {
        return mapper.read(id);
    }

    @Override
    public List<Board> getPage(int offset, int size) {
        return mapper.getPage(offset, size);
    }
}
