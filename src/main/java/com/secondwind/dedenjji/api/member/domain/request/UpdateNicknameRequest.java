package com.secondwind.dedenjji.api.member.domain.request;

import lombok.Data;

@Data
public class UpdateNicknameRequest {
    private Long id;
    private String nickname;
}
