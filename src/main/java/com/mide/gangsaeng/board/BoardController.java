package com.mide.gangsaeng.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    @Autowired
    public BoardController(BoardServiceImpl boardServiceImpl) {this.boardServiceImpl = boardServiceImpl;}

    @PostMapping
    public ResponseEntity<String> write() {
        boardServiceImpl.write();
        return ResponseEntity.ok("ok");
    }
}
