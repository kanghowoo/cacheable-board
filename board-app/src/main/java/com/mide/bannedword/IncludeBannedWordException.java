package com.mide.bannedword;

import com.mide.error.exception.BusinessException;
import com.mide.error.ErrorCode;

public class IncludeBannedWordException extends BusinessException {

    public IncludeBannedWordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
