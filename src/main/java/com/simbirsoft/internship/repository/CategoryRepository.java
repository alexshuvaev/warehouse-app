package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    /**
     * Validation by name. Category name must be unique.
     *
     * @param name of Category.
     * @return true if Category with this name exist, or false if not.
     */
    boolean existsByName(String name);
}
