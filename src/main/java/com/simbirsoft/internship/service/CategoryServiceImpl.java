package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.util.exception.MustBeUniqueException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity findById(int id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found Category with id=" + id));
    }

    @Transactional
    @Override
    public CategoryEntity update(CategoryEntity newCategory) {
        String name = checkUniqueName(newCategory.getName());
        int id = newCategory.getId();
        CategoryEntity category = findById(id);
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public CategoryEntity create(CategoryEntity newCategory) {
        checkUniqueName(newCategory.getName());
        return categoryRepository.save(newCategory);
    }

    @Transactional
    @Override
    public int getNextId() {
        return categoryRepository.genNextId();
    }

    private String checkUniqueName(String name){
        if (categoryRepository.existsByName(name)) {
            throw new MustBeUniqueException("Entity with name: " + name + " already exist (name must be unique)");
        }
        return name;
    }
}
