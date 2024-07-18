package com.mide.gangsaeng.board;

import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public class BoardRdbRepositoryImpl implements BoardRepository {

    private final BoardMybatisMapper mapper;

    public BoardRdbRepositoryImpl(BoardMybatisMapper mapper) {this.mapper = mapper;}

    public void write(Board board) {
        mapper.write(board);
    }

    @Override
    public Board read(long id) {
        return mapper.read(id);
    }

    @Override
    public List<Board> getPage(int offset, int size) {
        return mapper.getPage(offset, size);
    }

    @Override
    public List<Board> getPrevPage(long cursor, int size) {
        return mapper.getPrevPage(cursor, size);
    }

    @Override
    public List<Board> getNextPage(long cursor, int size) {
        return mapper.getNextPage(cursor, size);
    }
}
