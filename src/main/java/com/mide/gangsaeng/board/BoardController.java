package com.mide.gangsaeng.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {this.boardService = boardService;}

    @PostMapping
    public ResponseEntity<String> write() {
        boardService.write();
        return ResponseEntity.ok("ok");
    }
}
