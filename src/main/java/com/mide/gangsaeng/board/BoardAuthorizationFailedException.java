package com.mide.gangsaeng.board;

import com.mide.gangsaeng.common.error.ErrorCode;
import com.mide.gangsaeng.common.error.exception.BusinessException;

public class BoardAuthorizationFailedException extends BusinessException {
    public BoardAuthorizationFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
