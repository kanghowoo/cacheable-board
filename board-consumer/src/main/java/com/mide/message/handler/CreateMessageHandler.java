package com.mide.message.handler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mide.board.BoardProtoMapper;
import com.mide.board.BoardRepository;
import com.mide.gangsaeng.BoardProto.BoardCreateFailedMessage;
import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;
import com.mide.queue.MessageQueueService;
import com.mide.queue.ProtobufQueueMessage;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;

@Slf4j
@Component
public class CreateMessageHandler implements MessageHandler {

    private final BoardRepository boardRepository;
    private final BoardProtoMapper boardProtoMapper;

    public CreateMessageHandler(@Qualifier("boardRdbRepositoryImpl") BoardRepository boardRepository,
                                BoardProtoMapper boardProtoMapper) {
        this.boardRepository = boardRepository;
        this.boardProtoMapper = boardProtoMapper;
    }

    @Override
    public boolean supports(BoardFailedMessageWrapper wrapper) {
        return wrapper.hasCreate();
    }

    @Override
    public void handle(BoardFailedMessageWrapper wrapper, Message message) {

        try {
            boardRepository.write(boardProtoMapper.toEntity(wrapper.getCreate()));

        } catch (Exception e) {
            log.error("create handler failed", e);
        }
    }

}
