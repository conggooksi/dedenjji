package com.secondwind.dedenjji.api.club.clubMember.repository;

import com.secondwind.dedenjji.api.club.clubMember.domain.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>, ClubMemberCustomRepository {

    Optional<ClubMember> findByClubIdAndMemberId(Long id, Long currentMemberId);

    boolean existsByClubIdAndMemberId(Long clubId, Long memberId);
}
