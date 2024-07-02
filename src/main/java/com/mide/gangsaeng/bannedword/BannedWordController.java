package com.mide.gangsaeng.bannedword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/banned_words")
public class BannedWordController {

    private final BannedWordService bannedWordService;

    @Autowired
    public BannedWordController(BannedWordService bannedWordService) {
        this.bannedWordService = bannedWordService;
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CreateBannedWordRequest request) {
        bannedWordService.add(request.getWord());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> list() {
        BannedWordResponse<BannedWord> response = new BannedWordResponse<>(bannedWordService.list());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam String word) {
        bannedWordService.delete(word);
        return ResponseEntity.ok("deleted success");
    }
}
