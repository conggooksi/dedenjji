package com.secondwind.dedenjji.api.member.auth.service;

import com.secondwind.dedenjji.api.member.auth.domain.request.TokenRequestDTO;
import com.secondwind.dedenjji.api.member.domain.request.SignInRequest;
import com.secondwind.dedenjji.api.member.domain.request.SignUpRequest;
import com.secondwind.dedenjji.common.dto.TokenDTO;

public interface AuthService {
    Long signup(SignUpRequest memberJoinDTO);

    TokenDTO login(SignInRequest signInRequest);

    TokenDTO reissue(TokenRequestDTO tokenRequestDTO);

    void logout(TokenRequestDTO tokenRequestDTO);
}
