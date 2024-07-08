package com.mide.gangsaeng.board;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mide.gangsaeng.bannedword.BannedWordService;
import com.mide.gangsaeng.common.cursor.Cursor;
import com.mide.gangsaeng.common.cursor.CursorBasedRequest;
import com.mide.gangsaeng.common.cursor.CursorBasedResponse;

import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisException;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    public static final int MAX_VALUE_OF_PAGE_SIZE = 30;
    private final BoardRepository boardRepository;
    private final BannedWordService bannedWordService;
    private final RedisCommands<String, String> redisCommands;
    private final ObjectMapper objectMapper;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository,
                            BannedWordService bannedWordService,
                            RedisCommands<String, String> redisCommands,
                            ObjectMapper objectMapper) {
        this.boardRepository = boardRepository;
        this.bannedWordService = bannedWordService;
        this.redisCommands = redisCommands;
        this.objectMapper = objectMapper;
    }

    @Override
    public void write(BoardRequest request) {
        bannedWordService.validateBannedWords(request.getTitle() + " " +request.getContent());
        boardRepository.write(request);
    }

    @Override
    public BoardResponse read(long id) {
        Board board = fetchCachedBoard(id);

        if (board == null) {
            board = boardRepository.read(id);
            cacheBoard(board);
        }

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
    public CursorBasedResponse<Board> getPageByCursor(CursorBasedRequest request, int pageSize) {

        int size = pageSize + 1;
        Long prevCursor = null;
        Long nextCursor = null;

        if ("prev".equals(request.getDirection())) {
            List<Board> page = boardRepository.getPrevPage(request.getCursor(), size);

            page.sort((p1, p2) -> Long.compare(p2.getId(), p1.getId()));

            //이전 페이지에 새로운 데이터가 생길 경우
            if (page.size() < pageSize) {
                page = boardRepository.getNextPage(Long.MAX_VALUE, size); //root page;

                //다음 페이지 확인
                if (page.size() == size) {
                    page.remove(size - 1);
                    nextCursor = page.get(page.size() - 1).getId();
                }

                return new CursorBasedResponse<>(page, new Cursor(prevCursor, nextCursor));
            }

            if (page.size() == size) {
                page.remove(0);
                prevCursor = page.get(0).getId();
            }

            nextCursor = page.get(page.size() - 1).getId();

            return new CursorBasedResponse<>(page, new Cursor(prevCursor, nextCursor));
        }

        List<Board> page = boardRepository.getNextPage(request.getCursor(), size);

        if (page.size() == size) {
            page.remove(size - 1);
            nextCursor = page.get(page.size() - 1).getId();
        }


        if (!isRootCursor(request)) {
            prevCursor = page.get(0).getId();
        }

        return new CursorBasedResponse<>(page, new Cursor(prevCursor, nextCursor));
    }

    public void cacheBoard(Board board) {

        try {
            String boardValue = objectMapper.writeValueAsString(board);
            final long boardId = board.getId();
            redisCommands.set(makeBoardKey(boardId), boardValue, boardCachedDuration());
        } catch (JsonProcessingException | RedisException e) {
            log.error(e.getMessage(), e);
        }
    }

    private Board fetchCachedBoard(long id) {
        String boardRedisKey = makeBoardKey(id);
        String boardValue;

        try {
            boardValue = redisCommands.get(boardRedisKey);
        } catch (RedisException e) {
            log.error(e.getMessage(), e);
            return null;
        }

        if (boardValue == null) {
            return null;
        }

        try {
            return objectMapper.readValue(boardValue, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private SetArgs boardCachedDuration() {
        return SetArgs.Builder.ex(Duration.ofMillis(10_000));
    }

    private String makeBoardKey(long id) {
        return "board:" + id;
    }

    private boolean isRootCursor(CursorBasedRequest request) {
        return request.getCursor() == Long.MAX_VALUE && "next".equals(request.getDirection());
    }

}
