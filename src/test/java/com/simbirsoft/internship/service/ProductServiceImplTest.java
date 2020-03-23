package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.simbirsoft.internship.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(SpringRunner.class)
class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    /**
     * FindAll, FindById
     **/

    @Test
    void findById() {
        // given
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(PROD_ENTITY_1));
        // when
        ProductEntity result = productService.findById(1);
        // than
        assertEquals(PROD_ENTITY_1, result);
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void findAllById() {
        // given
        Set<Integer> ids = new HashSet<>(Arrays.asList(1, 2, 3));
        when(productRepository.findAllById(ids)).thenReturn(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2, PROD_ENTITY_3));
        // when
        List<ProductEntity> result = productService.findAllById(ids);
        // than
        assertIterableEquals(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2, PROD_ENTITY_3), result);
        verify(productRepository, times(1)).findAllById(ids);
    }

    @Test
    void findById_NotFound() {
        // given when
        // than
        assertThrows(NotFoundException.class, () -> productService.findById(100));
        verify(productRepository, times(1)).findById(100);
    }

    @Test
    void findAllById_NotFound() {
        // given
        Set<Integer> ids = new HashSet<>(Arrays.asList(1, 60, 2, 3, 50));
        when(productRepository.findAllById(ids)).thenReturn(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2, PROD_ENTITY_3));
        // than
        assertThrows(NotFoundException.class, () -> productService.findAllById(ids));
        verify(productRepository, times(1)).findAllById(ids);
    }

    /**
     * Create Invoice
     **/

    @Test
    void createNewInvoice() {
        // given
        when(productRepository.saveAll(anyCollection())).thenReturn(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2, PROD_ENTITY_3));
        when(productRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.findById(anyInt())).thenReturn(java.util.Optional.of(CAT_1));
        // when
        List<ProductEntity> result = productService.createList(Arrays.asList(PROD_DTO_1, PROD_DTO_2, PROD_DTO_3));
        // than
        assertIterableEquals(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2, PROD_ENTITY_3), result);
        verify(productRepository, times(1)).saveAll(anyCollection());
        verify(productRepository, times(3)).findByName(anyString());
        verify(categoryRepository, times(3)).findById(anyInt());
    }

    /**
     * Update Invoice
     **/

    @Test
    void createInvoice_UpdateAmountProducts() {
        // given
        when(productRepository.saveAll(anyCollection())).thenReturn(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2, PROD_ENTITY_3));
        when(productRepository.findByName(anyString())).thenReturn(java.util.Optional.of(PROD_ENTITY_1));
        // when
        List<ProductEntity> result = productService.createList(Arrays.asList(PROD_DTO_1, PROD_DTO_2, PROD_DTO_3));
        // than
        assertIterableEquals(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2, PROD_ENTITY_3), result);
        verify(productRepository, times(1)).saveAll(anyCollection());
        verify(productRepository, times(3)).findByName(anyString());
        verify(categoryRepository, times(0)).findById(anyInt());
    }

    /**
     * Create Invoice with Exception
     **/

    @Test
    void createNewInvoice_CatNotFound() {
        // given
        when(productRepository.saveAll(anyCollection())).thenReturn(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2, PROD_ENTITY_3));
        when(productRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.findById(anyInt())).thenReturn(java.util.Optional.of(CAT_1));
        // when
        assertThrows(InvalidPropertyException.class, () -> productService.createList(Arrays.asList(PROD_DTO_INVALID_CATID, PROD_DTO_2, PROD_DTO_3)));
        // than
        verifyNullTimes();
    }

    @Test
    void createInvoice_UpdateInvalidNameProducts() {
        // when
        assertThrows(InvalidPropertyException.class, () -> productService.createList(Arrays.asList(PROD_DTO_INVALID_NAME, PROD_DTO_2, PROD_DTO_3)));
        // than
        verifyNullTimes();
    }

    @Test
    void createInvoice_UpdateInvalidDescProducts() {
        // when
        assertThrows(InvalidPropertyException.class, () -> productService.createList(Arrays.asList(PROD_DTO_INVALID_DESC, PROD_DTO_2, PROD_DTO_3)));
        // than
        verifyNullTimes();
    }

    @Test
    void createInvoice_UpdateInvalidPriceProducts() {
        // when
        assertThrows(InvalidPropertyException.class, () -> productService.createList(Arrays.asList(PROD_DTO_INVALID_PRICE, PROD_DTO_2, PROD_DTO_3)));
        // than
        verifyNullTimes();
    }

    @Test
    void createInvoice_UpdateInvalidAmountProducts() {
        // when
        assertThrows(InvalidPropertyException.class, () -> productService.createList(Arrays.asList(PROD_DTO_INVALID_AMOUNT, PROD_DTO_2, PROD_DTO_3)));
        // than
        verifyNullTimes();
    }

    @Test
    void createInvoice_UpdateInvalidCatIdProducts() {
        // when
        assertThrows(InvalidPropertyException.class, () -> productService.createList(Arrays.asList(PROD_DTO_INVALID_CATID, PROD_DTO_2, PROD_DTO_3)));
        // than
        verifyNullTimes();
    }

    private void verifyNullTimes() {
        verify(productRepository, times(0)).saveAll(anyCollection());
        verify(productRepository, times(0)).findByName(anyString());
        verify(categoryRepository, times(0)).findById(anyInt());
    }
}