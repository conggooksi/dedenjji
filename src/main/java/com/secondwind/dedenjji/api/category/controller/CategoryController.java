package com.secondwind.dedenjji.api.category.controller;

import com.secondwind.dedenjji.api.category.domain.request.CategorySearchRequest;
import com.secondwind.dedenjji.api.category.domain.request.CreateCategoryRequest;
import com.secondwind.dedenjji.api.category.domain.request.UpdateCategoryRequest;
import com.secondwind.dedenjji.api.category.domain.response.CategoryResponse;
import com.secondwind.dedenjji.api.category.service.CategoryService;
import com.secondwind.dedenjji.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "카테고리 API")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 생성 API")
    @PostMapping("/new")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        Long categoryId = categoryService.createCategory(createCategoryRequest);

        return ResponseHandler.generate()
                .data(categoryId)
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "카테고리 슬라이스 조회 API")
    @GetMapping("")
    public ResponseEntity<?> getCategories(CategorySearchRequest categorySearchRequest) {
        Slice<CategoryResponse> categories = categoryService.getCategories(categorySearchRequest);

        return ResponseHandler.generate()
                .data(categories)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "카테고리 수정 API")
    @PatchMapping("")
    public ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest) {
        categoryService.updateCategory(updateCategoryRequest);
        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "카테고리 삭제 API")
    @DeleteMapping("/{category_id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "category_id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
