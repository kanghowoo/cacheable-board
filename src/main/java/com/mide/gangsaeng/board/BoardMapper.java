package com.mide.gangsaeng.board;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    void write(BoardRequest request);
    Board read(long id);
}
