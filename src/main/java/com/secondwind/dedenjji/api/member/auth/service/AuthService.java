package com.secondwind.dedenjji.api.member.auth.service;

import com.secondwind.dedenjji.api.member.domain.dto.MemberJoinDTO;

public interface AuthService {
    Long signup(MemberJoinDTO memberJoinDTO);
}
