package com.mide.gangsaeng.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mide.gangsaeng.common.Cursor;
import com.mide.gangsaeng.common.CursorBasedRequest;
import com.mide.gangsaeng.common.CursorBasedResponse;

@Service
public class BoardServiceImpl implements BoardService {

    public static final int MAX_VALUE_OF_PAGE_SIZE = 30;
    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {this.boardRepository =
            boardRepository;}

    public void write(BoardRequest request) {
        boardRepository.write(request);
    }

    @Override
    public BoardResponse read(long id) {
        Board board = boardRepository.read(id);
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .userId(board.getUserId())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }

    @Override
    public List<Board> getPage(int offset, int size) {
        size = Math.min(size, MAX_VALUE_OF_PAGE_SIZE);
        return boardRepository.getPage(offset, size);
    }

    @Override
    public CursorBasedResponse<Board> getPageByCursor(CursorBasedRequest request, int size) {
        if ("prev".equals(request.getDirection())) {
            List<Board> page = boardRepository.getPrevPage(request.getCursor(), size);
            page.sort((p1, p2) -> Long.compare(p2.getId(), p1.getId()));

            Cursor afterCursor = getCursor(page);

            return new CursorBasedResponse<>(page, afterCursor);
        }

        List<Board> page = boardRepository.getNextPage(request.getCursor(), size);
        Cursor afterCursor = getCursor(page);

        return new CursorBasedResponse<>(page, afterCursor);
    }

    private Cursor getCursor(List<Board> page) {
        if (page.isEmpty()) {
            throw new IllegalArgumentException("prev page is not present");
        }
        final long prevCursor = page.get(0).getId();
        final long nextCursor = page.get(page.size() - 1).getId();

        return new Cursor(prevCursor, nextCursor);
    }

}
