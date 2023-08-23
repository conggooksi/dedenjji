package com.secondwind.dedenjji.api.tournament.repository;

import com.secondwind.dedenjji.api.tournament.domain.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long>, TournamentCustomRepository {
}
