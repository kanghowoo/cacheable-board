package com.mide.gangsaeng.board;

public interface BoardService {
    void write(BoardRequest request);
    BoardResponse read(Long id);
}
