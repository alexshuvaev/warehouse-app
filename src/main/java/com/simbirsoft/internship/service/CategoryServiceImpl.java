package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.simbirsoft.internship.util.ValidationUtil.checkNotFoundWithId;
import static com.simbirsoft.internship.util.ValidationUtil.checkUniqueName;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity findById(Integer id) {
        return checkNotFoundWithId(categoryRepository.findById(id).orElse(null), id);
    }

    @Transactional
    @Override
    public CategoryEntity create(CategoryEntity categoryEntity) {
        return categoryRepository.save(checkUniqueName(categoryRepository.existsByName(categoryEntity.getName()), categoryEntity));
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Not found entity with id=" + id, null, true, false);
        }
    }

    @Transactional
    @Override
    public Integer getNextId() {
        return categoryRepository.genNextId();
    }
}
