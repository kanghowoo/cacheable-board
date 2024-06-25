package com.mide.gangsaeng.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

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

}
