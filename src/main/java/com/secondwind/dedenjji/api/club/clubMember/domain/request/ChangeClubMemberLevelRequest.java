package com.secondwind.dedenjji.api.club.clubMember.domain.request;

import com.secondwind.dedenjji.common.enumerate.Level;
import lombok.Data;

@Data
public class ChangeClubMemberLevelRequest {

    private Long clubId;
    private Long memberId;
    private Level level;
}
