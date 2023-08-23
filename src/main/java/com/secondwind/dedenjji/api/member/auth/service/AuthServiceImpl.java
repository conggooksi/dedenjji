package com.secondwind.dedenjji.api.member.auth.service;

import com.secondwind.dedenjji.api.member.domain.dto.MemberJoinDTO;
import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.api.member.domain.spec.PasswordSpecification;
import com.secondwind.dedenjji.api.member.repository.MemberRepository;
import com.secondwind.dedenjji.common.exception.CustomAuthException;
import com.secondwind.dedenjji.common.exception.code.AuthErrorCode;
import com.secondwind.dedenjji.common.result.JsonResultData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final PasswordSpecification passwordSpecification;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long signup(MemberJoinDTO memberJoinDTO) {
        passwordSpecification.check(memberJoinDTO.getPassword());

        if (memberRepository.existsByLoginIdAndIsDeletedFalse(memberJoinDTO.getLoginId())) {
            throw new CustomAuthException(JsonResultData.failResultBuilder()
                    .errorCode(AuthErrorCode.ALREADY_JOIN_USER.getCode())
                    .errorMessage(AuthErrorCode.ALREADY_JOIN_USER.getMessage())
                    .build());
        }

        if (memberRepository.existsByNicknameAndIsDeletedFalse(memberJoinDTO.getNickname())) {
            throw new CustomAuthException(JsonResultData.failResultBuilder()
                    .errorCode(AuthErrorCode.ALREADY_JOIN_USER.getCode())
                    .errorMessage(AuthErrorCode.ALREADY_JOIN_USER.getMessage())
                    .build());
        }

        Member member = memberJoinDTO.toMember(memberJoinDTO, passwordEncoder);

        return memberRepository.save(member).getId();
    }
}
