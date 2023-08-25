package com.secondwind.dedenjji.api.club.domain.request;

import com.secondwind.dedenjji.common.enumerate.Level;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateClubRequest {
    private Long id;
    private String name;
}
