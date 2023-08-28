package com.secondwind.dedenjji.api.club.clubMember.service;

import com.secondwind.dedenjji.api.club.clubMember.domain.entity.ClubMember;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.AddClubMemberRequest;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.AllowClubMemberRequest;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.ChangeClubMemberLevelRequest;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.DeleteClubMemberRequest;
import com.secondwind.dedenjji.api.club.domain.entity.Club;
import com.secondwind.dedenjji.api.club.clubMember.domain.response.ClubMemberResponse;
import com.secondwind.dedenjji.api.club.clubMember.repository.ClubMemberRepository;
import com.secondwind.dedenjji.api.club.repository.ClubRepository;
import com.secondwind.dedenjji.api.member.domain.entity.Member;
import com.secondwind.dedenjji.api.member.repository.MemberRepository;
import com.secondwind.dedenjji.common.enumerate.ClubAuthority;
import com.secondwind.dedenjji.common.enumerate.Level;
import com.secondwind.dedenjji.common.exception.ApiException;
import com.secondwind.dedenjji.common.exception.code.ClubErrorCode;
import com.secondwind.dedenjji.common.exception.code.ClubMemberErrorCode;
import com.secondwind.dedenjji.common.exception.code.MemberErrorCode;
import com.secondwind.dedenjji.common.utility.SecurityContextHolderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClubMemberServiceImpl implements ClubMemberService {
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

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

    @Override
    @Transactional
    public Long addClubMember(AddClubMemberRequest addClubMemberRequest) {
        if (clubMemberRepository.existsByClubIdAndMemberId(addClubMemberRequest.getClubId(), addClubMemberRequest.getMemberId())) {
            throw ApiException.builder()
                    .errorMessage(ClubMemberErrorCode.CLUBMEMBER_ALREADY_EXISTS.getMessage())
                    .errorCode(ClubMemberErrorCode.CLUBMEMBER_ALREADY_EXISTS.getCode())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Club club = clubRepository.findById(addClubMemberRequest.getClubId()).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(ClubErrorCode.CLUB_NOT_FOUND.getMessage())
                        .errorCode(ClubErrorCode.CLUB_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        Member member = memberRepository.findById(addClubMemberRequest.getMemberId()).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(MemberErrorCode.MEMBER_NOT_FOUND.getMessage())
                        .errorCode(MemberErrorCode.MEMBER_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        Long currentMemberId = SecurityContextHolderUtil.getCurrentMemberId();
        Optional<ClubMember> currentClubMember = clubMemberRepository.findByClubIdAndMemberId(addClubMemberRequest.getClubId(), currentMemberId);

        ClubMember clubMember = ClubMember.of()
                .member(member)
                .club(club)
                .level(Level.JOONGSU)
                .isAllowed(false)
                .clubAuthority(ClubAuthority.CLUB_USER)
                .build();

        if (currentClubMember.isPresent()) {
            if (currentClubMember.get().getClubAuthority().equals(ClubAuthority.CLUB_ADMIN)) {
                clubMember.makeIsAllowed();
            }
        }

        return clubMemberRepository.save(clubMember).getId();
    }

    @Override
    @Transactional
    public void allowClubMember(AllowClubMemberRequest allowClubMemberRequest) {
        Long currentMemberId = SecurityContextHolderUtil.getCurrentMemberId();

        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(allowClubMemberRequest.getClubId(), currentMemberId).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getMessage())
                        .errorCode(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        if (!clubMember.getClubAuthority().equals(ClubAuthority.CLUB_ADMIN)) {
            throw ApiException.builder()
                    .errorCode(ClubErrorCode.NOT_PERMITTED.getCode())
                    .errorMessage(ClubErrorCode.NOT_PERMITTED.getMessage())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(allowClubMemberRequest.getClubId(), allowClubMemberRequest.getMemberId()).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getMessage())
                        .errorCode(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        targetClubMember.makeIsAllowed();
    }

    @Override
    public ClubMember getClubMemberByClubIdAndMemberId(Long id, Long currentMemberId) {
        return clubMemberRepository.findByClubIdAndMemberId(id, currentMemberId).orElseThrow(
                () -> ApiException.builder()
                        .errorCode(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getCode())
                        .errorMessage(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
    }

    @Override
    @Transactional
    public void deleteClubMember(DeleteClubMemberRequest deleteClubMemberRequest) {
        Long currentMemberId = SecurityContextHolderUtil.getCurrentMemberId();

        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(deleteClubMemberRequest.getClubId(), currentMemberId).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getMessage())
                        .errorCode(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        if (!clubMember.getClubAuthority().equals(ClubAuthority.CLUB_ADMIN)) {
            throw ApiException.builder()
                    .errorCode(ClubErrorCode.NOT_PERMITTED.getCode())
                    .errorMessage(ClubErrorCode.NOT_PERMITTED.getMessage())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(deleteClubMemberRequest.getClubId(), deleteClubMemberRequest.getMemberId()).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getMessage())
                        .errorCode(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        clubMemberRepository.delete(targetClubMember);
    }

    @Override
    @Transactional
    public void changeClubMemberLevel(ChangeClubMemberLevelRequest changeClubMemberAuthorityRequest) {
        Long currentMemberId = SecurityContextHolderUtil.getCurrentMemberId();

        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(changeClubMemberAuthorityRequest.getClubId(), currentMemberId).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getMessage())
                        .errorCode(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        if (!clubMember.getClubAuthority().equals(ClubAuthority.CLUB_ADMIN)) {
            throw ApiException.builder()
                    .errorCode(ClubErrorCode.NOT_PERMITTED.getCode())
                    .errorMessage(ClubErrorCode.NOT_PERMITTED.getMessage())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        ClubMember targetClubMember = clubMemberRepository.findByClubIdAndMemberId(changeClubMemberAuthorityRequest.getClubId(), changeClubMemberAuthorityRequest.getMemberId()).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getMessage())
                        .errorCode(ClubMemberErrorCode.CLUBMEMBER_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        targetClubMember.changeClubMemberLevel(changeClubMemberAuthorityRequest.getLevel());
    }
}
