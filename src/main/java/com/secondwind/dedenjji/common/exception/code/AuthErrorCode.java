package com.secondwind.dedenjji.common.exception.code;

import lombok.Getter;

@Getter
public enum AuthErrorCode {
    NOT_AUTH_TOKEN("NOT_AUTH_TOKEN", "권한 정보가 없는 토큰입니다.");
    private final String code;
    private final String message;

    AuthErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
