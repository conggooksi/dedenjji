package com.secondwind.dedenjji.api.club.repository;

import com.secondwind.dedenjji.api.club.domain.entity.Club;
import com.secondwind.dedenjji.api.club.domain.request.ClubSearch;
import com.secondwind.dedenjji.api.club.domain.response.ClubResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ClubCustomRepository {
    Slice<ClubResponse> searchClub(ClubSearch clubSearch, PageRequest pageRequest);

    Optional<Club> findClub(Long id);
}
