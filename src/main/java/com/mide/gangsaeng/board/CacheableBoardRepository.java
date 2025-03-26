package com.mide.gangsaeng.board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mide.gangsaeng.queue.MessageQueueService;
import com.mide.gangsaeng.queue.message.BoardCreateFailedMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CacheableBoardRepository implements BoardRepository {
    private final BoardRepository db;
    private final BoardCacheRepositoryImpl cache;
    private final MessageQueueService messageQueueService;

    @Autowired
    public CacheableBoardRepository(
            @Qualifier("boardRdbRepositoryImpl") BoardRepository db,
            BoardCacheRepositoryImpl cache,
            MessageQueueService messageQueueService) {
        this.db = db;
        this.cache = cache;
        this.messageQueueService = messageQueueService;
    }

    @Override
    public void write(Board board) {
        Board boardDataToBeStored = board.toBuilder()
                                          .createdAt(LocalDateTime.now())
                                          .updatedAt(LocalDateTime.now())
                                          .build();
        try {
            db.write(boardDataToBeStored);
        } catch (Exception e) {
            messageQueueService.send(new BoardCreateFailedMessage(board.getTitle(), board.getContent()));
        }

        cache.write(boardDataToBeStored);
    }

    @Override
    public void update(Board board) {
        Board boardDataToBoStored = board.toBuilder()
                                         .updatedAt(LocalDateTime.now())
                                         .build();
        db.update(boardDataToBoStored);
        cache.update(boardDataToBoStored);
    }

    @Override
    public Board read(long id) {
        Board board = cache.read(id);

        if (board == null) {
            board = db.read(id);

            if (board == null) {
                return board;
            }
            cache.write(board);
        }

        return board;
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
