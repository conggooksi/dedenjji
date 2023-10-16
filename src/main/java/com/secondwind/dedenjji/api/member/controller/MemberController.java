package com.secondwind.dedenjji.api.member.controller;

import com.secondwind.dedenjji.api.member.domain.request.UpdateNicknameRequest;
import com.secondwind.dedenjji.api.member.service.MemberService;
import com.secondwind.dedenjji.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "멤버 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "닉네임 변경 API", description = "관리자가 다른 유저의 닉네임을 변경하려면 id에 수정하고 싶은 유저의 id를 넣어주세요. id가 null이라면 현재 로그인한 자신의 닉네임을 변경합니다.")
    @PatchMapping("/nickname")
    public ResponseEntity<?> updateNickname(@RequestBody UpdateNicknameRequest updateMemberRequest) {
        Long memberId = memberService.updateNickname(updateMemberRequest);

        return ResponseHandler.generate()
                .data(memberId)
                .status(HttpStatus.OK)
                .build();
    }
}
