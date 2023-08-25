package com.secondwind.dedenjji.api.club.clubMember.repository;

import com.querydsl.core.types.Projections;
import com.secondwind.dedenjji.api.club.clubMember.domain.entity.ClubMember;
import com.secondwind.dedenjji.api.club.clubMember.domain.response.ClubMemberResponse;
import com.secondwind.dedenjji.common.support.Querydsl4RepositorySupport;

import java.util.List;

import static com.secondwind.dedenjji.api.club.clubMember.domain.entity.QClubMember.clubMember;
import static com.secondwind.dedenjji.api.member.domain.entity.QMember.member;


public class ClubMemberRepositoryImpl extends Querydsl4RepositorySupport implements ClubMemberCustomRepository {
    public ClubMemberRepositoryImpl() {
        super(ClubMember.class);
    }

    @Override
    public List<ClubMemberResponse> findClubMembersByClubId(Long id) {
        return select(Projections.constructor(ClubMemberResponse.class,
                member.id,
                member.nickname,
                clubMember.clubAuthority,
                clubMember.isAllowed))
                .from(clubMember)
                .innerJoin(member).on(member.id.eq(clubMember.member.id))
                .where(clubMember.club.id.eq(id))
                .fetch();

    }
}
