package com.mide.gangsaeng.common.error;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
class ErrorResponse {
    private final String code;
    private final String message;
}
