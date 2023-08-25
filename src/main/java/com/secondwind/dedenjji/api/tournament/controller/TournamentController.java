package com.secondwind.dedenjji.api.tournament.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
