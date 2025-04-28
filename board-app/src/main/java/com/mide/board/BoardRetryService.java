package com.mide.board;

import org.springframework.stereotype.Service;

import com.mide.gangsaeng.BoardProto.BoardCreateFailedMessage;
import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;
import com.mide.gangsaeng.BoardProto.BoardUpdateFailedMessage;
import com.mide.queue.MessageQueueService;
import com.mide.queue.ProtobufQueueMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardRetryService {
    private final MessageQueueService messageQueueService;
    private final BoardProtoMapper boardProtoMapper;

    public BoardRetryService(MessageQueueService messageQueueService, BoardProtoMapper boardProtoMapper) {
        this.messageQueueService = messageQueueService;
        this.boardProtoMapper = boardProtoMapper;
    }

    public void sendCreateRetry(Board board) {
        BoardCreateFailedMessage retryMessage = boardProtoMapper.toCreateFailedMessage(board);

        BoardFailedMessageWrapper messageWrapper = BoardFailedMessageWrapper.newBuilder()
                                                                            .setCreate(retryMessage)
                                                                            .build();

        messageQueueService.send(new ProtobufQueueMessage<>(messageWrapper));
    }

    public void sendUpdateRetry(Board board) {
        BoardUpdateFailedMessage retryMessage = boardProtoMapper.toUpdateFailedMessage(board);

        BoardFailedMessageWrapper messageWrapper = BoardFailedMessageWrapper.newBuilder()
                                                                            .setUpdate(retryMessage)
                                                                            .build();

        messageQueueService.send(new ProtobufQueueMessage<>(messageWrapper));
    }

}
