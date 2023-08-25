package com.secondwind.dedenjji.api.member.service;

import com.secondwind.dedenjji.api.member.domain.request.UpdateNicknameRequest;

public interface MemberService {
    Long updateNickname(UpdateNicknameRequest updateMemberRequest);
}
