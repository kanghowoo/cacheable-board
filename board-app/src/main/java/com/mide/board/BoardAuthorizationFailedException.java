package com.mide.board;

import com.mide.error.ErrorCode;
import com.mide.error.exception.BusinessException;

public class BoardAuthorizationFailedException extends BusinessException {
    public BoardAuthorizationFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
