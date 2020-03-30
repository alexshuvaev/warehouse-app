package com.simbirsoft.internship.service.impl;

import com.simbirsoft.internship.entity.PurchaseEntity;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.repository.PurchaseRepository;
import com.simbirsoft.internship.repository.StoreRepository;
import com.simbirsoft.internship.service.PurchaseService;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
import com.simbirsoft.internship.util.exception.LowerThanAvaibleException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static com.simbirsoft.internship.testdata.PurchaseTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class PurchaseServiceImplTest {
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private PurchaseRepository purchaseRepository;
    @MockBean
    private StoreRepository storeRepository;
    @Autowired
    private PurchaseService purchaseService;

    @Test
    void makeAnPurchase() {
        // when
        when(storeRepository.findById(any())).thenReturn(java.util.Optional.of(STORE));
        when(productRepository.findAllById(anySet())).thenReturn(Collections.singletonList(AVAILABLE_PROD));
        when(purchaseRepository.save(any())).thenReturn(PURCHASE_ENTITY);
        // given
        PurchaseEntity result = purchaseService.makeAnPurchase(CORRECT_PURCHASE);
        // than
        assertEquals(PURCHASE_ENTITY.toString(), result.toString());
        verify(storeRepository, times(1)).findById(anyInt());
        verify(productRepository, times(1)).findAllById(anyIterable());
        verify(purchaseRepository, times(1)).save(any());
        verify(productRepository, times(1)).saveAll(anyIterable());
        verify(productRepository, times(0)).deleteAll(anyIterable());
    }

    @Test
    void makeAnPurchase_LowerThanAvailable() {
        // when
        when(storeRepository.findById(any())).thenReturn(java.util.Optional.of(STORE));
        when(productRepository.findAllById(anySet())).thenReturn(Collections.singletonList(AVAILABLE_PROD));
        // given
        assertThrows(LowerThanAvaibleException.class, () -> purchaseService.makeAnPurchase(PURCHASE_LOWER_THAN_AVAL));
        // than
        verify(storeRepository, times(1)).findById(anyInt());
        verify(productRepository, times(1)).findAllById(anyIterable());
        verify(purchaseRepository, times(0)).save(any());
        verify(productRepository, times(0)).saveAll(anyIterable());
        verify(productRepository, times(0)).deleteAll(anyIterable());
    }

    @Test
    void makeAnPurchase_ProductNotFound() {
        // when
        when(storeRepository.findById(any())).thenReturn(java.util.Optional.of(STORE));
        when(productRepository.findAllById(anySet())).thenReturn(Collections.emptyList());
        // given
        assertThrows(NotFoundException.class, () -> purchaseService.makeAnPurchase(CORRECT_PURCHASE));
        // than
        verify(storeRepository, times(1)).findById(any());
        verify(productRepository, times(1)).findAllById(anySet());
    }

    /**
     * Purchase Exception
     **/

    @Test
    void makeAnPurchase_InvalidStoreId() {
        // given
        assertThrows(InvalidPropertyException.class, () -> purchaseService.makeAnPurchase(PURCHASE_INVALID_STOREID));
        // than
        verifyNullTimes();
    }

    @Test
    void makeAnPurchase_InvalidAmountOfProduct() {
        // when
        when(storeRepository.findById(any())).thenReturn(Optional.of(STORE));
        // given
        assertThrows(InvalidPropertyException.class, () -> purchaseService.makeAnPurchase(PURCHASE_INVALID_AMOUNT));
        // than
        verify(storeRepository, times(1)).findById(anyInt());
        verifyNullTimes();
    }

    @Test
    void makeAnPurchase_InvalidIdOfProduct() {
        // when
        when(storeRepository.findById(any())).thenReturn(Optional.of(STORE));
        // given
        assertThrows(InvalidPropertyException.class, () -> purchaseService.makeAnPurchase(PURCHASE_INVALID_ID));
        // than
        verify(storeRepository, times(1)).findById(anyInt());
        verifyNullTimes();
    }

    /**
     * Verify
     **/

    private void verifyNullTimes() {
        verify(productRepository, times(0)).findById(anyInt());
        verify(purchaseRepository, times(0)).save(any());
        verify(productRepository, times(0)).saveAll(any());
        verify(productRepository, times(0)).delete(any());
    }
}