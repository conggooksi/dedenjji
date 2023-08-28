package com.secondwind.dedenjji.common.exception.code;

import lombok.Getter;

@Getter
public enum ClubMemberErrorCode {
    CLUBMEMBER_ALREADY_EXISTS("CLUBMEMBER_ALREADY_EXISTS", "이미 클럽에 속한 유저입니다."),
    NOT_ALLOWED("NOT_ALLOWED", "아직 승인 받지 못했습니다. 클럽장에서 문의해 주세요."),
    CLUBMEMBER_NOT_FOUND("CLUBMEMBER_NOT_FOUND", "클럽에 속한 유저를 찾지 못했습니다.");

    private final String code;
    private final String message;

    ClubMemberErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
