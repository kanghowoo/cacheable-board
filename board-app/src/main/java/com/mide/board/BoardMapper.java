package com.mide.board;

import org.mapstruct.Mapper;

import com.mide.mapper.BaseMapper;

@Mapper(config = BaseMapper.class)
public interface BoardMapper {
    Board boardRequestToBoard(BoardRequest request);
    BoardRequest toBoardRequest(Board board);
}
