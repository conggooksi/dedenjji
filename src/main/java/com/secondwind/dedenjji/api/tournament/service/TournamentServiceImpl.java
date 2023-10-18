package com.secondwind.dedenjji.api.tournament.service;

import com.secondwind.dedenjji.api.category.domain.entity.Category;
import com.secondwind.dedenjji.api.category.repository.CategoryRepository;
import com.secondwind.dedenjji.api.tournament.domain.entity.Tournament;
import com.secondwind.dedenjji.api.tournament.domain.request.CreateTournamentRequest;
import com.secondwind.dedenjji.api.tournament.repository.TournamentRepository;
import com.secondwind.dedenjji.common.exception.ApiException;
import com.secondwind.dedenjji.common.exception.code.CategoryErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TournamentServiceImpl implements TournamentService {
    private final TournamentRepository tournamentRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long createTournament(CreateTournamentRequest createTournamentRequest) {
        Category category = categoryRepository.findById(createTournamentRequest.getCategoryId()).orElseThrow(
                () -> ApiException.builder()
                        .errorCode(CategoryErrorCode.CATEGORY_NOT_FOUND.getCode())
                        .errorMessage(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        Tournament tournament = Tournament.of()
                .category(category)
                .name(createTournamentRequest.getName())
                .build();

        Long tournamentId = tournamentRepository.save(tournament).getId();

        return tournamentId;
    }
}
