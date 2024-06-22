package com.mide.gangsaeng.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepositoryImpl boardRepositoryImpl;

    @Autowired
    public BoardServiceImpl(BoardRepositoryImpl boardRepositoryImpl) {this.boardRepositoryImpl =
            boardRepositoryImpl;}

    public void write() {
        boardRepositoryImpl.write();
    }

}
