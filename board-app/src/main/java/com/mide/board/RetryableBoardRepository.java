package com.mide.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mide.gangsaeng.BoardProto.BoardCreateFailedMessage;
import com.mide.gangsaeng.BoardProto.BoardFailedMessageWrapper;
import com.mide.gangsaeng.BoardProto.BoardUpdateFailedMessage;
import com.mide.queue.MessageQueueService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RetryableBoardRepository implements BoardRepository{
    private final BoardRepository db;
    private final BoardRetryService boardRetryService;

    @Autowired
    public RetryableBoardRepository(@Qualifier("boardRdbRepositoryImpl") BoardRepository db,
                                    BoardRetryService boardRetryService) {
        this.db = db;
        this.boardRetryService = boardRetryService;

    }

    @Override
    public void write(Board board) {
        try {
            db.write(board);
        } catch (Exception e) {
            log.info("board write failed. : {}", e.getMessage());
            boardRetryService.sendCreateRetry(board);
        }
    }

    @Override
    public void update(Board board) {
        try {
            db.update(board);
        } catch (Exception e) {
            log.info("board update failed. : {}", e.getMessage());
            boardRetryService.sendUpdateRetry(board);
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
