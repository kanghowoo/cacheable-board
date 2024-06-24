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

}
