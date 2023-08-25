package com.secondwind.dedenjji.api.club.clubMember.repository;

import com.secondwind.dedenjji.api.club.clubMember.domain.response.ClubMemberResponse;

import java.util.List;

public interface ClubMemberCustomRepository {
    List<ClubMemberResponse> findClubMembersByClubId(Long id);
}
