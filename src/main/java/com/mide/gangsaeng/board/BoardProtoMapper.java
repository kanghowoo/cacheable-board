package com.mide.gangsaeng.board;

import org.mapstruct.Mapper;

import com.mide.gangsaeng.BoardProto.BoardCreateFailedMessage;
import com.mide.gangsaeng.BoardProto.BoardUpdateFailedMessage;
import com.mide.gangsaeng.common.mapper.BaseMapper;

@Mapper(config = BaseMapper.class)
public interface BoardProtoMapper {
    BoardCreateFailedMessage createFailedToProtobuf(Board board);
    BoardUpdateFailedMessage updateFailedToProtobuf(Board board);
}
