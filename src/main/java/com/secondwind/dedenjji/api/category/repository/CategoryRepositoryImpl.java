package com.secondwind.dedenjji.api.category.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.secondwind.dedenjji.api.category.domain.entity.Category;
import com.secondwind.dedenjji.api.category.domain.request.CategorySearchRequest;
import com.secondwind.dedenjji.api.category.domain.response.CategoryResponse;
import com.secondwind.dedenjji.common.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.util.StringUtils;

import static com.secondwind.dedenjji.api.category.domain.entity.QCategory.category;

public class CategoryRepositoryImpl extends Querydsl4RepositorySupport implements CategoryCustomRepository {
    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public Slice<CategoryResponse> searchCategories(CategorySearchRequest categorySearchRequest, PageRequest pageRequest) {
        return applySlicing(pageRequest, contentQuery ->
                contentQuery.select(Projections.constructor(CategoryResponse.class,
                        category.id,
                        category.name))
                        .from(category)
                        .where(categoryNameLike(categorySearchRequest.getCategoryName())));
    }

    private BooleanExpression categoryNameLike(String categoryName) {
        return StringUtils.hasText(categoryName) ? category.name.contains(categoryName) : null;
    }
}
