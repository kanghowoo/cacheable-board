package com.mide.gangsaeng.board;

public interface BoardRepository {
    void write(BoardRequest request);

    Board read(Long id);
}
