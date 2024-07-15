package com.mide.gangsaeng.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mide.gangsaeng.common.cursor.CursorBasedRequest;
import com.mide.gangsaeng.common.cursor.CursorBasedResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/boards")
public class BoardController {

    public static final String DEFAULT_PAGE = "1";
    public static final String DEFAULT_SIZE = "10";
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {this.boardService = boardService;}

    @PostMapping
    public ResponseEntity<?> write(@Valid @RequestBody BoardRequest request) {
        boardService.write(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable Long id) {
        BoardResponse boardResponse = boardService.read(id);
        return new ResponseEntity<>(boardResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        int offset = (page - 1) * size;
        List<Board> boardList = boardService.getPage(offset, size);

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @GetMapping("/cursor")
    public ResponseEntity<?> cursorList(CursorBasedRequest request) {
        CursorBasedResponse<Board> response =
                boardService.getPageByCursor(request, request.getSize());

        return ResponseEntity.ok(response);
    }
}
