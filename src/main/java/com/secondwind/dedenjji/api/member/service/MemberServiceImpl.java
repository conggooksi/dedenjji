package com.secondwind.dedenjji.api.member.service;

import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.api.member.domain.request.UpdateNicknameRequest;
import com.secondwind.dedenjji.api.member.repository.MemberRepository;
import com.secondwind.dedenjji.common.enumerate.Authority;
import com.secondwind.dedenjji.common.exception.ApiException;
import com.secondwind.dedenjji.common.exception.code.MemberErrorCode;
import com.secondwind.dedenjji.common.utility.SecurityContextHolderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long updateNickname(UpdateNicknameRequest updateMemberRequest) {
        Long memberId;
        if (updateMemberRequest.getId() == null) {
            memberId = SecurityContextHolderUtil.getCurrentMemberId();
        } else {
            Authority currentAuthority = SecurityContextHolderUtil.getCurrentAuthority();
            if (!currentAuthority.equals(Authority.ROLE_ADMIN)) {
                throw ApiException.builder()
                        .status(HttpStatus.FORBIDDEN)
                        .errorMessage(MemberErrorCode.PERMISSION_DENIED.getMessage())
                        .errorCode(MemberErrorCode.PERMISSION_DENIED.getCode())
                        .build();
            }
            memberId = updateMemberRequest.getId();
        }
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> ApiException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errorMessage(MemberErrorCode.MEMBER_NOT_FOUND.getMessage())
                        .errorCode(MemberErrorCode.MEMBER_NOT_FOUND.getCode())
                        .build()
        );

        member.changeNickname(updateMemberRequest.getNickname());
        return member.getId();
    }
}
