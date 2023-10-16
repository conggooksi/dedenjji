package com.secondwind.dedenjji.api.category.controller;

import com.secondwind.dedenjji.api.category.domain.request.CreateCategoryRequest;
import com.secondwind.dedenjji.api.category.service.CategoryService;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.AddClubMemberRequest;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.AllowClubMemberRequest;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.ChangeClubMemberLevelRequest;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.DeleteClubMemberRequest;
import com.secondwind.dedenjji.api.club.clubMember.service.ClubMemberService;
import com.secondwind.dedenjji.api.club.domain.request.ClubSearch;
import com.secondwind.dedenjji.api.club.domain.request.CreateClubRequest;
import com.secondwind.dedenjji.api.club.domain.request.UpdateClubRequest;
import com.secondwind.dedenjji.api.club.domain.response.ClubDetail;
import com.secondwind.dedenjji.api.club.domain.response.ClubResponse;
import com.secondwind.dedenjji.api.club.service.ClubService;
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
}
