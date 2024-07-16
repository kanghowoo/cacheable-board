package com.mide.gangsaeng.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mide.gangsaeng.common.error.ErrorCode;

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
    public void write(BoardRequest request) {
        db.write(request);
    }

    @Override
    public Board read(long id) {
        Board board = cache.read(id);

        if (board == null) {
            board = db.read(id);
            Optional.ofNullable(board)
                            .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));

            cache.cacheBoard(board);
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
