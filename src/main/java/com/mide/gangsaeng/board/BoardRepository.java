package com.mide.gangsaeng.board;

import java.util.List;

public interface BoardRepository {
    void write(Board board);
    void update(Board board);
    Board read(long id);
    List<Board> getPage(int offset, int size);

    List<Board> getPrevPage(long cursor, int size);

    List<Board> getNextPage(long cursor, int size);
}
