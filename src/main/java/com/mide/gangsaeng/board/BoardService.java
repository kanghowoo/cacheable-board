package com.mide.gangsaeng.board;

import java.util.List;

public interface BoardService {
    void write(BoardRequest request);
    BoardResponse read(long id);
    List<Board> getPage(int offset, int size);
}
