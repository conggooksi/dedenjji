package com.secondwind.dedenjji.api.club.clubMember.service;

import com.secondwind.dedenjji.api.club.clubMember.domain.entity.ClubMember;
import com.secondwind.dedenjji.api.club.domain.entity.Club;
import com.secondwind.dedenjji.api.club.clubMember.domain.response.ClubMemberResponse;
import com.secondwind.dedenjji.api.club.clubMember.repository.ClubMemberRepository;
import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.common.enumerate.ClubAuthority;
import com.secondwind.dedenjji.common.enumerate.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClubMemberServiceImpl implements ClubMemberService {
    private final ClubMemberRepository clubMemberRepository;

    @Override
    @Transactional
    public Long createClubMember(Member member, Club club, Level level, ClubAuthority clubAuthority) {
        ClubMember clubMember = ClubMember.of()
                .member(member)
                .club(club)
                .level(level)
                .clubAuthority(clubAuthority)
                .isAllowed(true)
                .build();

        return clubMemberRepository.save(clubMember).getId();
    }

    @Override
    public List<ClubMemberResponse> getClubMembers(Long id) {
        List<ClubMemberResponse> clubMembers = clubMemberRepository.findClubMembersByClubId(id);

        return clubMembers;
    }
}
