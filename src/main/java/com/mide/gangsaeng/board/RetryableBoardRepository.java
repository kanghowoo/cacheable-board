package com.mide.gangsaeng.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mide.gangsaeng.queue.MessageQueueService;
import com.mide.gangsaeng.queue.message.BoardCreateFailedMessage;

@Repository
public class RetryableBoardRepository implements BoardRepository{
    private final BoardRepository db;
    private final MessageQueueService messageQueueService;

    @Autowired
    public RetryableBoardRepository(@Qualifier("boardRdbRepositoryImpl") BoardRepository db,
                                    MessageQueueService messageQueueService) {
        this.db = db;
        this.messageQueueService = messageQueueService;
    }

    @Override
    public void write(Board board) {
        try {
            db.write(board);
        } catch (Exception e) {
            messageQueueService.send(
                    new BoardCreateFailedMessage(board));
        }
    }

    @Override
    public void update(Board board) {
        try {
            db.update(board);
        } catch (Exception e) {
            messageQueueService.send(
                    new BoardCreateFailedMessage(board));
        }
    }

    @Override
    public Board read(long id) {
        return db.read(id);
    }

    @Override
    public List<Board> getPage(int offset, int size) {
        return db.getPage(offset, size);
    }

    @Override
    public List<Board> getPrevPage(long cursor, int size) {
        return db.getPrevPage(cursor, size);
    }

    @Override
    public List<Board> getNextPage(long cursor, int size) {
        return db.getNextPage(cursor, size);
    }
}
