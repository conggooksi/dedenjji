package com.secondwind.dedenjji.api.category.repository;

import com.secondwind.dedenjji.api.category.domain.request.CategorySearchRequest;
import com.secondwind.dedenjji.api.category.domain.response.CategoryResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

public interface CategoryCustomRepository {
    Slice<CategoryResponse> searchCategories(CategorySearchRequest categorySearchRequest, PageRequest pageRequest);
}
