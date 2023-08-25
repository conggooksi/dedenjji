package com.secondwind.dedenjji.common.exception.code;

import lombok.Getter;

@Getter
public enum ClubErrorCode {
    CLUB_NOT_FOUND("CLUB_NOT_FOUND", "클럽을 찾지 못했습니다."),
    DUPLICATED_CLUB_NAME("DUPLICATED_CLUB_NAME", "이미 존재하는 클럽이름입니다.");

    private final String code;
    private final String message;

    ClubErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
