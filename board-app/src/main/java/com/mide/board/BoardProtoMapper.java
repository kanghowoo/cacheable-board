package com.mide.board;

import org.mapstruct.Mapper;

import com.mide.mapper.BaseMapper;
import com.mide.mapper.ProtoDateMapper;
import com.mide.gangsaeng.BoardProto.BoardCreateFailedMessage;
import com.mide.gangsaeng.BoardProto.BoardUpdateFailedMessage;

@Mapper(
        config = BaseMapper.class,
        uses = { ProtoDateMapper.class }
)
public interface BoardProtoMapper {
    BoardUpdateFailedMessage toUpdateFailedMessage(Board board);
    BoardCreateFailedMessage toCreateFailedMessage(Board board);

    Board toEntity(BoardUpdateFailedMessage message);
    Board toEntity(BoardCreateFailedMessage message);

}
