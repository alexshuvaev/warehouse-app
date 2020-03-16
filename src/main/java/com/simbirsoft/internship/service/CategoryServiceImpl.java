package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.to.category.CategoryWithId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public CategoryEntity update(CategoryEntity newCategoryEntity) {
        String name = newCategoryEntity.getName();
        Integer id = newCategoryEntity.getId();
        boolean existByName = categoryRepository.existsByName(name);
        CategoryEntity categoryEntity = findById(id);
        categoryEntity.setName(name);
        return categoryRepository.save(checkUniqueName(existByName, categoryEntity));
    }

    @Transactional
    @Override
    public CategoryEntity create(CategoryEntity categoryEntity) {
        return categoryRepository.save(checkUniqueName(categoryRepository.existsByName(categoryEntity.getName()), categoryEntity));
    }

    @Transactional
    @Override
    public List<CategoryWithId> createList(List<String> categoriesNames) {
        List<CategoryEntity> categoryList = categoryRepository.saveAll(
                categoriesNames.stream()
                        .filter(e -> !categoryRepository.existsByName(e))
                        .map(e -> new CategoryEntity(getNextId(), e))
                        .collect(Collectors.toList()));

        return categoryList.stream()
                .map(e -> new CategoryWithId(e.getId(), e.getName(), e.getProducts().size()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Integer getNextId() {
        return categoryRepository.genNextId();
    }
}
