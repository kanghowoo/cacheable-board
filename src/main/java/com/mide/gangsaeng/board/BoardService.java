package com.mide.gangsaeng.board;

import java.util.List;

import com.mide.gangsaeng.common.CursorBasedRequest;
import com.mide.gangsaeng.common.CursorBasedResponse;

public interface BoardService {
    void write(BoardRequest request);
    BoardResponse read(long id);
    List<Board> getPage(int offset, int size);
    CursorBasedResponse<Board> getPageByCursor(CursorBasedRequest request, int size);
}
