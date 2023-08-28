package com.secondwind.dedenjji.api.club.clubMember.domain.request;

import lombok.Data;

@Data
public class DeleteClubMemberRequest {

    private Long clubId;
    private Long memberId;
}
