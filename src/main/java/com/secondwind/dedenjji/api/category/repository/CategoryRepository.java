package com.secondwind.dedenjji.api.category.repository;

import com.secondwind.dedenjji.api.category.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustomRepository {
    boolean existsByName(String categoryName);
}
