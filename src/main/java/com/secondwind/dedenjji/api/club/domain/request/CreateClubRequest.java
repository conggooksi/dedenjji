package com.secondwind.dedenjji.api.club.domain.request;

import com.secondwind.dedenjji.common.enumerate.Level;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateClubRequest {
    @NotBlank(message = "클럽 이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "자신의 수준을 선택해주세요.")
    private Level level;
}
