package com.secondwind.dedenjji.api.club.service;

import com.secondwind.dedenjji.api.club.domain.entity.Club;
import com.secondwind.dedenjji.api.club.domain.request.ClubSearch;
import com.secondwind.dedenjji.api.club.domain.request.CreateClubRequest;
import com.secondwind.dedenjji.api.club.domain.request.UpdateClubRequest;
import com.secondwind.dedenjji.api.club.domain.response.ClubDetail;
import com.secondwind.dedenjji.api.club.domain.response.ClubResponse;
import com.secondwind.dedenjji.api.club.repository.ClubRepository;
import com.secondwind.dedenjji.api.club.clubMember.domain.response.ClubMemberResponse;
import com.secondwind.dedenjji.api.club.clubMember.service.ClubMemberService;
import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.api.member.repository.MemberRepository;
import com.secondwind.dedenjji.common.enumerate.ClubAuthority;
import com.secondwind.dedenjji.common.exception.ApiException;
import com.secondwind.dedenjji.common.exception.code.ClubErrorCode;
import com.secondwind.dedenjji.common.exception.code.MemberErrorCode;
import com.secondwind.dedenjji.common.utility.SecurityContextHolderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;
    private final ClubMemberService clubMemberService;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long createClub(CreateClubRequest makeClubRequest) {
        Long memberId = SecurityContextHolderUtil.getCurrentMemberId();
        if (clubRepository.existsByName(makeClubRequest.getName())) {
            throw ApiException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .errorMessage(ClubErrorCode.DUPLICATED_CLUB_NAME.getMessage())
                    .errorCode(ClubErrorCode.DUPLICATED_CLUB_NAME.getCode())
                    .build();
        }
        Club club = Club.of()
                .name(makeClubRequest.getName())
                .build();

        clubRepository.save(club);

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> ApiException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errorMessage(MemberErrorCode.MEMBER_NOT_FOUND.getMessage())
                        .errorCode(MemberErrorCode.MEMBER_NOT_FOUND.getCode())
                        .build());


        Long clubMemberId = clubMemberService.createClubMember(member, club, makeClubRequest.getLevel(), ClubAuthority.TEAM_ADMIN);

        return clubMemberId;
    }

    @Override
    public Slice<ClubResponse> getClubs(ClubSearch clubSearch) {
        PageRequest pageRequest = PageRequest.of(clubSearch.getOffset(), clubSearch.getLimit(), clubSearch.getDirection(), clubSearch.getOrderBy());
        Slice<ClubResponse> clubResponses = clubRepository.searchClub(clubSearch, pageRequest);
        return clubResponses;
    }

    @Override
    public ClubDetail getClub(Long id) {
        Club club = clubRepository.findClub(id).orElseThrow(
                () -> ApiException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errorMessage(ClubErrorCode.CLUB_NOT_FOUND.getMessage())
                        .errorCode(ClubErrorCode.CLUB_NOT_FOUND.getCode())
                        .build());

        List<ClubMemberResponse> clubMembers = clubMemberService.getClubMembers(id);
        ClubDetail clubDetail = ClubDetail.toDTO(club, clubMembers);

        return clubDetail;
    }

    @Override
    @Transactional
    public Long updateClub(UpdateClubRequest updateClubRequest) {
        Club club = clubRepository.findById(updateClubRequest.getId()).orElseThrow(
                () -> ApiException.builder()
                        .errorCode(ClubErrorCode.CLUB_NOT_FOUND.getCode())
                        .errorMessage(ClubErrorCode.CLUB_NOT_FOUND.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        club.updateClubBuilder()
                .name(updateClubRequest.getName())
                .build();
        return club.getId();
    }

    @Override
    @Transactional
    public void deleteClub(Long id) {
        Club club = clubRepository.findById(id).orElseThrow(
                () -> ApiException.builder()
                        .errorCode(ClubErrorCode.CLUB_NOT_FOUND.getCode())
                        .errorMessage(ClubErrorCode.CLUB_NOT_FOUND.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        clubRepository.delete(club);
    }
}
