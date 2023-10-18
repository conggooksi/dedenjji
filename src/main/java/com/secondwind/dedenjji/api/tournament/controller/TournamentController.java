package com.secondwind.dedenjji.api.tournament.controller;

import com.secondwind.dedenjji.api.tournament.domain.request.CreateTournamentRequest;
import com.secondwind.dedenjji.api.tournament.domain.request.TournamentSearchRequest;
import com.secondwind.dedenjji.api.tournament.domain.request.UpdateTournamentRequest;
import com.secondwind.dedenjji.api.tournament.domain.response.TournamentResponse;
import com.secondwind.dedenjji.api.tournament.service.TournamentService;
import com.secondwind.dedenjji.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "tournament", description = "Tournament API")
@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
@Slf4j
public class TournamentController {

    private final TournamentService tournamentService;

    @Operation(summary = "토너먼트 생성 API")
    @PostMapping("/new")
    public ResponseEntity<?> createTournament(@RequestBody CreateTournamentRequest createTournamentRequest) {
        Long tournamentId = tournamentService.createTournament(createTournamentRequest);

        return ResponseHandler.generate()
                .data(tournamentId)
                .status(HttpStatus.CREATED)
                .build();
    }

//@Tag(name = "tournament", description = "Tournament API")
//@RestController
//@RequestMapping("/api/tournaments")
//@RequiredArgsConstructor
//@Slf4j
//public class TournamentController {
//
//    @Operation(summary = "tournament 생성 API")
//    @PostMapping("/new")
//    public ResponseEntity<?> createTournament() {
//
//        return null;
//    }
//
//
//
//}
