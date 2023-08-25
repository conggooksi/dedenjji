package com.secondwind.dedenjji.api.club.controller;

import com.secondwind.dedenjji.api.club.domain.request.ClubSearch;
import com.secondwind.dedenjji.api.club.domain.request.CreateClubRequest;
import com.secondwind.dedenjji.api.club.domain.request.UpdateClubRequest;
import com.secondwind.dedenjji.api.club.domain.response.ClubDetail;
import com.secondwind.dedenjji.api.club.domain.response.ClubResponse;
import com.secondwind.dedenjji.api.club.service.ClubService;
import com.secondwind.dedenjji.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Club", description = "클럽 API")
@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
@Slf4j
public class ClubController {
    private final ClubService clubService;

    @Operation(summary = "클럽 생성 API")
    @PostMapping("/new")
    public ResponseEntity<?> createClub(@Valid @RequestBody CreateClubRequest makeClubRequest) {
        Long clubId = clubService.createClub(makeClubRequest);
        return ResponseHandler.generate()
                .data(clubId)
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "클럽 슬라이스 조회 API")
    @GetMapping("")
    public ResponseEntity<?> getClubs(ClubSearch clubSearch) {
        Slice<ClubResponse> clubResponses = clubService.getClubs(clubSearch);
        return ResponseHandler.generate()
                .data(clubResponses)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "클럽 상세 조회 API")
    @GetMapping("/{club_id}")
    public ResponseEntity<?> getClub(@PathVariable(value = "club_id") Long id) {
        ClubDetail clubDetails = clubService.getClub(id);
        return ResponseHandler.generate()
                .data(clubDetails)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "클럽 수정 API")
    @PatchMapping("")
    public ResponseEntity<?> updateClub(@RequestBody UpdateClubRequest updateClubRequest) {
        Long id = clubService.updateClub(updateClubRequest);
        return ResponseHandler.generate()
                .data(id)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "클럽 삭제 API")
    @DeleteMapping("/{club_id}")
    public ResponseEntity<?> deleteClub(@PathVariable(value = "club_id") Long id) {
        clubService.deleteClub(id);
        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
