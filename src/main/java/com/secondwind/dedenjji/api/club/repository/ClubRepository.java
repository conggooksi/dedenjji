package com.secondwind.dedenjji.api.club.repository;

import com.secondwind.dedenjji.api.club.domain.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubCustomRepository {
    boolean existsByName(String name);
}
