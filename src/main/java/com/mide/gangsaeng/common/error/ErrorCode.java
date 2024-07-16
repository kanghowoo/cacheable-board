package com.mide.gangsaeng.common.error;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INCLUDE_BANNED_WORD("BW001", "include banned word", BAD_REQUEST),
    BOARD_NOT_FOUND("B001", "board not found", NOT_FOUND)
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
