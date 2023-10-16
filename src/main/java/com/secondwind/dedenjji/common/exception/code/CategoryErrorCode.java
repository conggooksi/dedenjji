package com.secondwind.dedenjji.common.exception.code;

import lombok.Getter;

@Getter
public enum CategoryErrorCode {
    ALREADY_EXISTS("ALREADY_EXISTS", "이미 존재하는 카테고리입니다."),
    NOT_PERMITTED("NOT_PERMITTED", "권한이 없습니다."),
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", "카테고리를 찾지 못했습니다.");

    private final String code;
    private final String message;

    CategoryErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
