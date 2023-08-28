package com.secondwind.dedenjji.api.club.clubMember.domain.request;

import com.secondwind.dedenjji.common.enumerate.ClubAuthority;
import lombok.Builder;
import lombok.Data;

@Data
public class AddClubMemberRequest {

    private Long clubId;
    private Long memberId;
}
