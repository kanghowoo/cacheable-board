package com.mide.gangsaeng.board;

import java.util.List;

import com.mide.gangsaeng.common.Cursor;

public interface BoardRepository {
    void write(BoardRequest request);
    Board read(long id);
    List<Board> getPage(int offset, int size);

    List<Board> getPrevPage(long cursor, int size);

    List<Board> getNextPage(long cursor, int size);
}
