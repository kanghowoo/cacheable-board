package com.mide.message.handler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mide.board.BoardProtoMapper;
import com.mide.board.BoardRepository;
import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.model.Message;

@Slf4j
@Component
public class UpdateMessageHandler implements MessageHandler {

    private final BoardRepository boardRepository;
    private final BoardProtoMapper boardProtoMapper;


    public UpdateMessageHandler(@Qualifier("retryableBoardRepository") BoardRepository boardRepository,
                                BoardProtoMapper boardProtoMapper) {
        this.boardRepository = boardRepository;
        this.boardProtoMapper = boardProtoMapper;
    }

    @Override
    public boolean supports(BoardFailedMessageWrapper wrapper) {
        return wrapper.hasUpdate();
    }

    @Override
    public void handle(BoardFailedMessageWrapper wrapper, Message message) {
        try {
            boardRepository.update(boardProtoMapper.toEntity(wrapper.getUpdate()));
        } catch (Exception e) {
            log.error("update handler failed", e);
        }
    }
}
