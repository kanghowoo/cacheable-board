package com.mide.gangsaeng.board;

import com.mide.gangsaeng.common.error.ErrorCode;
import com.mide.gangsaeng.common.error.exception.BusinessException;

class BoardNotFoundException extends BusinessException {
    public BoardNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
