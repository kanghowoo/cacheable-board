package com.mide.gangsaeng.board;

import java.util.List;

public interface BoardRepository {
    void write(BoardRequest request);
    Board read(long id);
    List<Board> getPage(int offset, int size);
}
