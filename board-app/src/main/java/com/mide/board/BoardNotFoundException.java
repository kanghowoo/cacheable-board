package com.mide.board;

import com.mide.error.ErrorCode;
import com.mide.error.exception.BusinessException;

class BoardNotFoundException extends BusinessException {
    public BoardNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
