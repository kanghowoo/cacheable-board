package com.mide.gangsaeng.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardReadServiceImpl implements BoardReadService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardReadServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
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
