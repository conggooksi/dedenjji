package com.secondwind.dedenjji.api.tournament.service;

import com.secondwind.dedenjji.api.tournament.domain.request.CreateTournamentRequest;

public interface TournamentService {
    Long createTournament(CreateTournamentRequest createTournamentRequest);
}
