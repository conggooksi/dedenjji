package com.secondwind.dedenjji.api.category.service;

import com.secondwind.dedenjji.api.category.domain.entity.Category;
import com.secondwind.dedenjji.api.category.domain.request.CategorySearchRequest;
import com.secondwind.dedenjji.api.category.domain.request.CreateCategoryRequest;
import com.secondwind.dedenjji.api.category.domain.request.UpdateCategoryRequest;
import com.secondwind.dedenjji.api.category.domain.response.CategoryResponse;
import com.secondwind.dedenjji.api.category.repository.CategoryRepository;
import com.secondwind.dedenjji.common.exception.ApiException;
import com.secondwind.dedenjji.common.exception.code.CategoryErrorCode;
import com.secondwind.dedenjji.common.utility.SecurityContextHolderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    @Transactional
    public Long createCategory(CreateCategoryRequest createCategoryRequest) {
        SecurityContextHolderUtil.checkRoleAdmin();

        String categoryName = createCategoryRequest.getName();
        if (categoryRepository.existsByName(categoryName)) {
            throw ApiException.builder()
                    .errorCode(CategoryErrorCode.ALREADY_EXISTS.getCode())
                    .errorMessage(CategoryErrorCode.ALREADY_EXISTS.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Category category = Category.of()
                .name(categoryName)
                .build();

        Long categoryId = categoryRepository.save(category).getId();
        return categoryId;
    }

    @Override
    public Slice<CategoryResponse> getCategories(CategorySearchRequest categorySearchRequest) {
        PageRequest pageRequest = PageRequest.of(categorySearchRequest.getOffset(), categorySearchRequest.getLimit(), categorySearchRequest.getDirection(), categorySearchRequest.getOrderBy());
        Slice<CategoryResponse> categoryResponses = categoryRepository.searchCategories(categorySearchRequest, pageRequest);
        return categoryResponses;
    }

    @Override
    @Transactional
    public void updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        SecurityContextHolderUtil.checkRoleAdmin();

        Category category = categoryRepository.findById(updateCategoryRequest.getId()).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
                        .errorCode(CategoryErrorCode.CATEGORY_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        category.updateCategoryBuilder()
                .name(updateCategoryRequest.getName())
                .build();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        SecurityContextHolderUtil.checkRoleAdmin();

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> ApiException.builder()
                        .errorMessage(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
                        .errorCode(CategoryErrorCode.CATEGORY_NOT_FOUND.getCode())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        categoryRepository.delete(category);
    }
}
