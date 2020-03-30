package com.simbirsoft.internship.service.impl;

import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.repository.WriteOffRepository;
import com.simbirsoft.internship.service.ProductService;
import com.simbirsoft.internship.service.WriteOffService;
import com.simbirsoft.internship.util.exception.AlreadyConfirmedException;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
import com.simbirsoft.internship.util.exception.LowerThanAvaibleException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.simbirsoft.internship.testdata.WriteOffTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class WriteOffServiceImplTest {
    @MockBean
    private WriteOffRepository writeOffRepository;
    @MockBean
    private ProductService productService;
    @MockBean
    ProductRepository productRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private WriteOffService writeOffService;

    /**
     * Find Write-Off
     **/

    @Test
    void findAll() {
        // given
        when(writeOffRepository.findAll()).thenReturn(WRITEOFF_ENTITIES);
        // when
        List<WriteOffEntity> result = writeOffService.findAll();
        // than
        assertIterableEquals(WRITEOFF_ENTITIES, result);
        verify(writeOffRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        // given
        when(writeOffRepository.findById(1)).thenReturn(java.util.Optional.of(WRITEOFF_FALSE));
        // when
        WriteOffEntity result = writeOffService.findById(1);
        // than
        assertEquals(WRITEOFF_FALSE, result);
        verify(writeOffRepository, times(1)).findById(1);
    }

    /**
     * Create WriteOff
     **/

    @Test
    void createWriteOff() {
        // given
        when(productService.findAllById(anySet())).thenReturn(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2));
        // when
        WriteOffEntity result = writeOffService.createWriteOff(WRITEOFF);
        // than
        assertEquals(WRITEOFF.getPositions().size(), result.getProducts().size());
        verify(productRepository, times(1)).saveAll(Arrays.asList(PROD_ENTITY_1, PROD_ENTITY_2));
        verify(productRepository, times(0)).deleteAll(anyCollection());
        verify(writeOffRepository, times(1)).save(any());
    }

    @Test
    void createWriteOff_LowerThanAvailable() {
        // given
        when(productService.findAllById(anySet())).thenReturn(Collections.singletonList(PROD_ENTITY_1));
        // when
        assertThrows(LowerThanAvaibleException.class, () -> writeOffService.createWriteOff(WRITEOFF_LOWER_THAN_AVAL));
        // than
        verify(productRepository, times(0)).saveAll(anyCollection());
        verify(productRepository, times(0)).deleteAll(anyCollection());
        verify(writeOffRepository, times(0)).save(any());
    }

    @Test
    void createWriteOff_InvalidProductId() {
        // when
        assertThrows(InvalidPropertyException.class, () -> writeOffService.createWriteOff(WRITEOFF_INVALID_PRODUCTID));
        // than
        verify(productRepository, times(0)).saveAll(anyCollection());
        verify(productRepository, times(0)).deleteAll(anyCollection());
        verify(writeOffRepository, times(0)).save(any());
    }

    @Test
    void createWriteOff_InvalidAmountId() {
        // when
        assertThrows(InvalidPropertyException.class, () -> writeOffService.createWriteOff(WRITEOFF_INVALID_AMOUNT));
        // than
        verify(productRepository, times(0)).saveAll(anyCollection());
        verify(productRepository, times(0)).deleteAll(anyCollection());
        verify(writeOffRepository, times(0)).save(any());
    }

    /**
     * Confirm Write-Off
     **/

    @Test
    void confirm() {
        // given
        when(writeOffRepository.findById(anyInt())).thenReturn(Optional.of(WRITEOFF_FALSE));
        // when
        String result = writeOffService.confirm(1, "qwerty");
        // than
        assertEquals("Write-off successfully done", result);
        verify(writeOffRepository, times(1)).save(any());
    }

    @Test
    void confirm_WriteOffNotFound() {
        // given
        when(writeOffRepository.findById(anyInt())).thenReturn(Optional.empty());
        // when
        assertThrows(NotFoundException.class, () -> writeOffService.confirm(100, "qwerty"));
        // than
        verify(writeOffRepository, times(0)).save(any());
    }

    @Test
    void confirm_ConfirmedAlready() {
        // given
        when(writeOffRepository.findById(anyInt())).thenReturn(Optional.of(WRITEOFF_TRUE));
        // when
        assertThrows(AlreadyConfirmedException.class, () -> writeOffService.confirm(2, "qwerty"));
        // than
        verify(writeOffRepository, times(0)).save(any());
    }

    @Test
    void confirm_WrongConfirmCode() {
        // when
        String result = writeOffService.confirm(1, "zxcvb");
        // than
        assertEquals("Please, try again. Confirmation code is not correct!", result);
        verify(writeOffRepository, times(0)).findById(any());
        verify(writeOffRepository, times(0)).save(any());
    }

    /**
     * Delete Write-Off
     **/

    @Test
    void deleteById_WrongConfirmCode() {
        // when
        String result = writeOffService.deleteById(1, "zxcvb");
        // than
        assertEquals("Please, try again. Confirmation code is not correct!", result);
        verify(writeOffRepository, times(0)).findById(any());
        verify(writeOffRepository, times(0)).save(any());
        verify(productRepository, times(0)).findById(any());
        verify(categoryRepository, times(0)).findById(any());
        verify(productRepository, times(0)).saveAll(any());
        verify(writeOffRepository, times(0)).deleteById(any());
    }

    @Test
    void deleteById_NotFoundException() {
        // given
        when(writeOffRepository.findById(anyInt())).thenReturn(Optional.of(WRITEOFF_FALSE));
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(CAT_1));
        // when
        String result = writeOffService.deleteById(1,"qwerty");
        // than
        assertEquals("Write-off is canceled. Products quantities are restored.", result);
        verify(writeOffRepository, times(1)).findById(any());
        verify(productRepository, times(2)).findById(any());
        verify(categoryRepository, times(2)).findById(any());
        verify(productRepository, times(1)).saveAll(any());
        verify(writeOffRepository, times(1)).deleteById(any());
    }
}