package com.secondwind.dedenjji.api.club.clubMember.service;

import com.secondwind.dedenjji.api.club.domain.entity.Club;
import com.secondwind.dedenjji.api.club.clubMember.domain.response.ClubMemberResponse;
import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.common.enumerate.ClubAuthority;
import com.secondwind.dedenjji.common.enumerate.Level;

import java.util.List;

public interface ClubMemberService {
    Long createClubMember(Member member, Club club, Level level, ClubAuthority clubAuthority);

    List<ClubMemberResponse> getClubMembers(Long id);
}
