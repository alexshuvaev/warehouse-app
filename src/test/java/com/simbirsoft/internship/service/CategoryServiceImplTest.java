package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.simbirsoft.internship.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    /**
     * FindAll, FindById
     **/

    @Test
    void findAllTest() {
        // given
        when(categoryRepository.findAll()).thenReturn(CATEGORY_ENTITIES);
        // when
        List<CategoryEntity> result = categoryService.findAll();
        // than
        assertIterableEquals(CATEGORY_ENTITIES, result);
        verify(categoryRepository, times(1)).findAll();

    }

    @Test
    void findByIdTest() {
        // given
        when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(CAT_1));
        // when
        CategoryEntity result = categoryService.findById(1);
        // than
        assertEquals(CAT_1, result);
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    void findById_NotFoundTest() {
        // given, when
        // than
        assertThrows(NotFoundException.class, () -> categoryService.findById(100));
        verify(categoryRepository, times(1)).findById(100);
    }

    /**
     * Create Category
     **/

    @Test
    void createTest() {
        // given
        when(categoryRepository.save(CAT_1)).thenReturn(CAT_1);
        // when
        CategoryEntity result = categoryService.create(CAT_1);
        // than
        assertEquals(CAT_1, result);
        verify(categoryRepository, times(1)).save(CAT_1);
    }

    @Test
    void create_NotUniqueNameTest() {
        // given
        when(categoryRepository.existsByName(DUPLICATE_NAME_CAT.getName())).thenReturn(true);
        when(categoryRepository.save(DUPLICATE_NAME_CAT)).thenThrow(InvalidPropertyException.class);
        // than
        assertThrows(InvalidPropertyException.class, () -> categoryService.create(DUPLICATE_NAME_CAT));
        verify(categoryRepository, times(1)).existsByName(DUPLICATE_NAME_CAT.getName());
        verify(categoryRepository, times(0)).save(DUPLICATE_NAME_CAT);
    }

    @Test
    void create_EmptyNameTest() {
        // given
        when(categoryRepository.save(EMPTY_CAT_NAME)).thenThrow(InvalidPropertyException.class);
        // than
        assertThrows(InvalidPropertyException.class, () -> categoryService.create(EMPTY_CAT_NAME));
        verify(categoryRepository, times(0)).save(EMPTY_CAT_NAME);
    }

    @Test
    void create_SpecialCharsNameTest() {
        // given
        when(categoryRepository.save(INVALID_CAT_NAME)).thenThrow(InvalidPropertyException.class);
        // than
        assertThrows(InvalidPropertyException.class, () -> categoryService.create(INVALID_CAT_NAME));
        verify(categoryRepository, times(0)).save(INVALID_CAT_NAME);
    }

    @Test
    void create_OnlySpacesNameTest() {
        // given
        when(categoryRepository.save(ONLY_SPACE_CAT_NAME)).thenThrow(InvalidPropertyException.class);
        // than
        assertThrows(InvalidPropertyException.class, () -> categoryService.create(ONLY_SPACE_CAT_NAME));
        verify(categoryRepository, times(0)).save(ONLY_SPACE_CAT_NAME);
    }

    /**
     * Update Category
     **/

    @Test
    void updateTest() {
        // given
        when(categoryRepository.findById(CAT_1.getId())).thenReturn(java.util.Optional.of(CAT_1));
        when(categoryRepository.save(CAT_FOR_UPDATE)).thenReturn(CAT_FOR_UPDATE);
        // when
        CategoryEntity result = categoryService.update(CAT_FOR_UPDATE);
        // than
        assertEquals(CAT_1, result);
        verify(categoryRepository, times(1)).findById(CAT_FOR_UPDATE.getId());
        verify(categoryRepository, times(1)).save(CAT_FOR_UPDATE);
    }

}