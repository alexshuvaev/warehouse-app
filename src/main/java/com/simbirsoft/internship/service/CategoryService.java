package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    /**
     * Find all Categories
     *
     * @return List of Categories.
     */
    List<CategoryEntity> findAll();

    /**
     * Find Category by id.
     *
     * @param id of Category.
     * @return Category entity if not null. If Category=null will be NotFoundException.
     */
    CategoryEntity findById(int id);

    /**
     * Update exist Category.
     *
     * @param newCategoryEntity exist Category with new name.
     * @return updated Category.
     * If Category not found will be NotFoundException.
     * If newCategoryEntity name not unique will be MustBeUniqueException.
     */
    CategoryEntity update(CategoryEntity newCategoryEntity);

    /**
     * Creat new Category.
     *
     * @param categoryEntity new Category entity.
     * @return new created Category.
     * If CategoryEntity name not unique will be MustBeUniqueException.
     */
    CategoryEntity create(CategoryEntity categoryEntity);

    /**
     * Get next id.
     *
     * @return next id from global sequences.
     */
    int getNextId();
}
