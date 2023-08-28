package com.secondwind.dedenjji.api.club.clubMember.domain.request;

import lombok.Data;

@Data
public class AllowClubMemberRequest {

    private Long clubId;
    private Long memberId;
}
