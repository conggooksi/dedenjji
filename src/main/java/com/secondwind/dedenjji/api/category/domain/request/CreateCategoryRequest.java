package com.secondwind.dedenjji.api.category.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequest {
    @NotBlank(message = "카테고리 이름을 입력해주세요.")
    private String name;
}
