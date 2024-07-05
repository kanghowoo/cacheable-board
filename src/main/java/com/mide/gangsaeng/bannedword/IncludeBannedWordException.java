package com.mide.gangsaeng.bannedword;

import com.mide.gangsaeng.common.error.ErrorCode;
import com.mide.gangsaeng.common.error.exception.BusinessException;

public class IncludeBannedWordException extends BusinessException {

    public IncludeBannedWordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
