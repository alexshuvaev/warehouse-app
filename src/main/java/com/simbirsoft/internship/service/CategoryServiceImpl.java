package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
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
        return categoryRepository.findById((id)).orElseThrow(
                () -> new NotFoundException("Not found Category with id=" + id));
    }

    @Transactional
    @Override
    public CategoryEntity update(CategoryEntity newCategory) {
        categoryValidation(newCategory);
        CategoryEntity category = findById(newCategory.getId());
        category.setName(newCategory.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public CategoryEntity create(CategoryEntity newCategory) {
        categoryValidation(newCategory);
        return categoryRepository.save(newCategory);
    }

    /**
     * Category validation
     **/

    private void categoryValidation(CategoryEntity categoryEntity) {
        nameValidation(categoryEntity.getName());
        existByName(categoryEntity.getName());
    }

    private void existByName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new InvalidPropertyException("Entity with name: " + name + " already exist (name must be unique)");
        }
    }

    private void nameValidation(String name) {
        // https://stackoverflow.com/a/44232658/12890862
        if (name.trim().isEmpty() || name.length() < 2 || name.length() > 60 || !name.matches("[\\p{L}\\d\\s]+")) {
            throw new InvalidPropertyException("The Category name length must be between 2 and 60 chars, and no special characters.");
        }
    }
}
