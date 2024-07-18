package com.mide.gangsaeng.board;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CacheableBoardRepository implements BoardRepository {
    private final BoardRepository db;
    private final BoardCacheRepositoryImpl cache;

    @Autowired
    public CacheableBoardRepository(
            @Qualifier("boardRdbRepositoryImpl") BoardRepository db,
            BoardCacheRepositoryImpl cache) {
        this.db = db;
        this.cache = cache;
    }

    @Override
    public void write(Board board) {
        Board boardDataToBeStored = board.toBuilder()
                                          .createdAt(LocalDateTime.now())
                                          .updatedAt(LocalDateTime.now())
                                          .build();
        db.write(boardDataToBeStored);
        cache.write(boardDataToBeStored);
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
