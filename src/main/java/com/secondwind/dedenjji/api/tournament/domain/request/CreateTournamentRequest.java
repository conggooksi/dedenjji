package com.secondwind.dedenjji.api.tournament.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTournamentRequest {
    @NotBlank(message = "카테고리를 선택해주세요.")
    private Long categoryId;
    @NotBlank(message = "토너먼트 이름을 입력해주세요.")
    private String name;
}