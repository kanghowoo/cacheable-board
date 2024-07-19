package com.mide.gangsaeng.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMybatisMapper {
    void write(Board board);
    void update(Board board);
    Board read(long id);
    List<Board> getPage(int offset, int size);
    List<Board> getPrevPage(long id, int size);
    List<Board> getNextPage(long id, int size);
}
