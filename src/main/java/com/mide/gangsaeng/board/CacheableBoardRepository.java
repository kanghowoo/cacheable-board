package com.mide.gangsaeng.board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CacheableBoardRepository implements BoardRepository {
    private final BoardRepository retryableDb;
    private final BoardCacheRepositoryImpl cache;

    @Autowired
    public CacheableBoardRepository(
            @Qualifier("retryableBoardRepository") BoardRepository retryableDb,
            BoardCacheRepositoryImpl cache) {
        this.retryableDb = retryableDb;
        this.cache = cache;
    }

    @Override
    public void write(Board board) {
        Board boardDataToBeStored = board.toBuilder()
                                          .createdAt(LocalDateTime.now())
                                          .updatedAt(LocalDateTime.now())
                                          .build();
        retryableDb.write(boardDataToBeStored);
        cache.write(boardDataToBeStored);
    }

    @Override
    public void update(Board board) {
        Board boardDataToBoStored = board.toBuilder()
                                         .updatedAt(LocalDateTime.now())
                                         .build();
        retryableDb.update(boardDataToBoStored);
        cache.update(boardDataToBoStored);
    }

    @Override
    public Board read(long id) {
        Board board = cache.read(id);

        if (board == null) {
            board = retryableDb.read(id);

            if (board == null) {
                return board;
            }
            cache.write(board);
        }

        return board;
    }

    @Override
    public List<Board> getPage(int offset, int size) {
        return retryableDb.getPage(offset, size);
    }

    @Override
    public List<Board> getPrevPage(long cursor, int size) {
        return retryableDb.getPrevPage(cursor, size);
    }

    @Override
    public List<Board> getNextPage(long cursor, int size) {
        return retryableDb.getNextPage(cursor, size);
    }
}
