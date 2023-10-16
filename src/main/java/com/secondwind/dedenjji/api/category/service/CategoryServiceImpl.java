package com.secondwind.dedenjji.api.category.service;

import com.secondwind.dedenjji.api.category.domain.entity.Category;
import com.secondwind.dedenjji.api.category.domain.request.CreateCategoryRequest;
import com.secondwind.dedenjji.api.category.repository.CategoryRepository;
import com.secondwind.dedenjji.common.enumerate.Authority;
import com.secondwind.dedenjji.common.exception.ApiException;
import com.secondwind.dedenjji.common.exception.code.CategoryErrorCode;
import com.secondwind.dedenjji.common.utility.SecurityContextHolderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        Authority currentAuthority = SecurityContextHolderUtil.getCurrentAuthority();
        if (currentAuthority != Authority.ROLE_ADMIN) {
            throw ApiException.builder()
                    .errorMessage(CategoryErrorCode.NOT_PERMITTED.getMessage())
                    .errorCode(CategoryErrorCode.NOT_PERMITTED.getCode())
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

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
}
