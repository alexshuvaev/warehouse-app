package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.simbirsoft.internship.CategoryTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Test
    void findAll() {
        given(categoryRepository.findAll())
                .willReturn(CATEGORY_ENTITIES);
        List<CategoryEntity> categoryEntities = categoryService.findAll();
        assertIterableEquals(CATEGORY_ENTITIES, categoryEntities);
    }

    @Test
    void findById() {
        given(categoryRepository.findById(any()))
                .willReturn(java.util.Optional.of(CAT_1));
        CategoryEntity categoryEntity = categoryService.findById(1);
        assertThat(categoryEntity.getId()).isEqualTo(1);
    }

    @Test
    void create() {
        given(categoryRepository.save(any()))
                .willReturn(NEW_CAT);
        CategoryEntity categoryEntity = categoryService.create(NEW_CAT);
        assertThat(NEW_CAT).isEqualTo(categoryEntity);
    }
}