package com.secondwind.dedenjji.common.exception.code;

import lombok.Getter;

@Getter
public enum ClubMemberErrorCode {
    CLUBMEMBER_NOT_FOUND("CLUBMEMBER_NOT_FOUND", "클럽에 속한 유저를 찾지 못했습니다.");

    private final String code;
    private final String message;

    ClubMemberErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
