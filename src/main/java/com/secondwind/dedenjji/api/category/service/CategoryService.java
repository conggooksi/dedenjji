package com.secondwind.dedenjji.api.category.service;

import com.secondwind.dedenjji.api.category.domain.request.CategorySearchRequest;
import com.secondwind.dedenjji.api.category.domain.request.CreateCategoryRequest;
import com.secondwind.dedenjji.api.category.domain.request.UpdateCategoryRequest;
import com.secondwind.dedenjji.api.category.domain.response.CategoryResponse;
import org.springframework.data.domain.Slice;

public interface CategoryService {
    Long createCategory(CreateCategoryRequest createCategoryRequest);

    Slice<CategoryResponse> getCategories(CategorySearchRequest categorySearchRequest);

    void updateCategory(UpdateCategoryRequest updateCategoryRequest);

    void deleteCategory(Long id);
}
