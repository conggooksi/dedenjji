package com.secondwind.dedenjji.api.category.domain.request;

import com.secondwind.dedenjji.common.dto.PaginationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class CategorySearchRequest extends PaginationDto {
    private String categoryName;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public CategorySearchRequest(Integer limit, Integer offset, String orderBy, Sort.Direction direction, String categoryName) {
        super(limit, offset, orderBy, direction);
        this.categoryName = categoryName;
    }
}
