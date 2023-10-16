package com.secondwind.dedenjji.api.category.service;

import com.secondwind.dedenjji.api.category.domain.request.CreateCategoryRequest;

public interface CategoryService {
    Long createCategory(CreateCategoryRequest createCategoryRequest);
}
