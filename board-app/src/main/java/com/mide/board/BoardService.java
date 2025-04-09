package com.mide.board;

import java.util.List;

import com.mide.cursor.CursorBasedRequest;
import com.mide.cursor.CursorBasedResponse;

public interface BoardService {
    void write(BoardRequest request);
    void update(long id, BoardRequest request);
    BoardResponse read(long id);
    List<Board> getPage(int offset, int size);
    CursorBasedResponse<Board> getPageByCursor(CursorBasedRequest request, int size);
}
