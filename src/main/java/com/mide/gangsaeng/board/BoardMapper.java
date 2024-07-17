package com.mide.gangsaeng.board;

import org.mapstruct.Mapper;

import com.mide.gangsaeng.common.mapper.BaseMapper;

@Mapper(config = BaseMapper.class)
public interface BoardMapper {
    Board boardRequestToBoard(BoardRequest request);
}
