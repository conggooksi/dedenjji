package com.secondwind.dedenjji.api.club.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.secondwind.dedenjji.api.club.domain.entity.Club;
import com.secondwind.dedenjji.api.club.domain.request.ClubSearch;
import com.secondwind.dedenjji.api.club.domain.response.ClubResponse;
import com.secondwind.dedenjji.common.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.secondwind.dedenjji.api.club.domain.entity.QClub.club;

public class ClubRepositoryImpl extends Querydsl4RepositorySupport implements ClubCustomRepository {
    public ClubRepositoryImpl() {
        super(Club.class);
    }

    @Override
    public Slice<ClubResponse> searchClub(ClubSearch clubSearch, PageRequest pageRequest) {
        return applySlicing(pageRequest, contentQuery ->
                contentQuery.select(Projections.constructor(ClubResponse.class,
                        club.id,
                        club.name))
                        .from(club)
                        .where(clubNameLike(clubSearch.getClubName())));

    }

    @Override
    public Optional<Club> findClub(Long id) {
        return Optional.ofNullable(selectFrom(club)
                .where(club.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression clubNameLike(String clubName) {
        return StringUtils.hasText(clubName) ? club.name.contains(clubName) : null;
    }
}
