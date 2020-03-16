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
    CategoryEntity findById(Integer id);

    /**
     * Creat new Category.
     *
     * @param categoryEntity new Category entity.
     * @return new created Category.
     * If CategoryEntity name not unique will be MustBeUniqueException.
     */
    CategoryEntity create(CategoryEntity categoryEntity);

    /**
     * Delete category by id.
     *
     * @param id of Category which have to delete.
     * If Category not found with this id in DB, than will be NotFoundException.
     */
    void deleteById(int id);

    /**
     * Get next id.
     *
     * @return next id from global sequences.
     */
    Integer getNextId();
}
