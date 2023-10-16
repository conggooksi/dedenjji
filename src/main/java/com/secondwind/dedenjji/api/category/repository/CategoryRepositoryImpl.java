package com.secondwind.dedenjji.api.category.repository;

import com.secondwind.dedenjji.api.category.domain.entity.Category;
import com.secondwind.dedenjji.common.support.Querydsl4RepositorySupport;

public class CategoryRepositoryImpl extends Querydsl4RepositorySupport implements CategoryCustomRepository {
    public CategoryRepositoryImpl() {
        super(Category.class);
    }
}
