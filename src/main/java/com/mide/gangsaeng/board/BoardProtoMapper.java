package com.mide.gangsaeng.board;

import org.mapstruct.Mapper;

import com.mide.gangsaeng.BoardProto;
import com.mide.gangsaeng.common.mapper.BaseMapper;
import com.mide.gangsaeng.common.mapper.ProtoDateMapper;

@Mapper(
        config = BaseMapper.class,
        uses = { ProtoDateMapper.class }
)
public interface BoardProtoMapper {
    BoardProto.BoardCreateFailedMessage toCreateFailedMessage(Board board);
    BoardProto.BoardUpdateFailedMessage toUpdateFailedMessage(Board board);
}
