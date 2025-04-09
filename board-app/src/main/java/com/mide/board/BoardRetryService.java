package com.mide.board;

import org.springframework.stereotype.Service;

import com.mide.gangsaeng.BoardProto.BoardCreateFailedMessage;
import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;
import com.mide.gangsaeng.BoardProto.BoardUpdateFailedMessage;
import com.mide.queue.MessageQueueService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardRetryService {
    public static final int MAX_RETRY_COUNT = 5;
    private final MessageQueueService messageQueueService;
    private final BoardProtoMapper boardProtoMapper;

    public BoardRetryService(MessageQueueService messageQueueService, BoardProtoMapper boardProtoMapper) {
        this.messageQueueService = messageQueueService;
        this.boardProtoMapper = boardProtoMapper;
    }

    public void sendCreateRetry(Board board) {
        BoardCreateFailedMessage message = boardProtoMapper.toCreateFailedMessage(board);

        int retryCount = message.getRetryCount();

        if (isRetryLimitExceeded(retryCount)) {
            log.warn("Exceeded retry limit : {}", retryCount);
            return;
        }

        BoardCreateFailedMessage retryMessage =
                message.toBuilder().setRetryCount(retryCount + 1).build();

        BoardFailedMessageWrapper messageWrapper = BoardFailedMessageWrapper.newBuilder()
                                                                            .setCreate(retryMessage)
                                                                            .build();

        messageQueueService.send(messageWrapper);
    }

    public void sendUpdateRetry(Board board) {
        BoardUpdateFailedMessage message = boardProtoMapper.toUpdateFailedMessage(board);

        int retryCount = message.getRetryCount();

        if (isRetryLimitExceeded(retryCount)) {
            log.warn("Exceeded retry limit : {}", retryCount);
            return;
        }

        BoardUpdateFailedMessage retryMessage =
                message.toBuilder().setRetryCount(retryCount + 1).build();

        BoardFailedMessageWrapper messageWrapper = BoardFailedMessageWrapper.newBuilder()
                                                                            .setUpdate(retryMessage)
                                                                            .build();

        messageQueueService.send(messageWrapper);
    }

    private boolean isRetryLimitExceeded(int count) {
        return count > MAX_RETRY_COUNT;
    }

}
