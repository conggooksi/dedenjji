package com.secondwind.dedenjji.api.club.clubMember.domain.response;

import com.secondwind.dedenjji.common.enumerate.ClubAuthority;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubMemberResponse {
    private Long memberId;
    private String nickname;
    private ClubAuthority clubAuthority;
    private boolean isAllowed;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public ClubMemberResponse(Long memberId, String nickname, ClubAuthority clubAuthority, boolean isAllowed) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.clubAuthority = clubAuthority;
        this.isAllowed = isAllowed;
    }

    public static ClubMemberResponse toDTO(Long memberId, String nickname, ClubAuthority clubAuthority, boolean isAllowed) {
        return ClubMemberResponse.of()
                .memberId(memberId)
                .nickname(nickname)
                .clubAuthority(clubAuthority)
                .isAllowed(isAllowed)
                .build();
    }
}
