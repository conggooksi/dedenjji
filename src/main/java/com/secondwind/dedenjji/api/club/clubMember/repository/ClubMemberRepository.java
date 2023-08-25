package com.secondwind.dedenjji.api.club.clubMember.repository;

import com.secondwind.dedenjji.api.club.clubMember.domain.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>, ClubMemberCustomRepository {

}
