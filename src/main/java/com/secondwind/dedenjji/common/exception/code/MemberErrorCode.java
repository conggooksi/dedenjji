package com.secondwind.dedenjji.common.exception.code;

import lombok.Getter;

@Getter
public enum MemberErrorCode {
    PERMISSION_DENIED("PERMISSION_DENIED", "권한이 없습니다."),

    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", "멤버를 찾지 못했습니다."),
    ENTERED_ID_AND_PASSWORD("ENTERED_ID_AND_PASSWORD", "아이디와 비밀번호를 입력해주세요.");
    private final String code;
    private final String message;

    MemberErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
