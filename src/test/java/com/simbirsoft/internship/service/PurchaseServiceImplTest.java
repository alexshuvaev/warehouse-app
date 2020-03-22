package com.simbirsoft.internship.service;

import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.repository.PurchaseRepository;
import com.simbirsoft.internship.repository.StoreRepository;
import com.simbirsoft.internship.dto.product.ProductWithId;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
import com.simbirsoft.internship.util.exception.LowerThanAvaibleException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.simbirsoft.internship.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
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
    void makeAnPurchaseTest() {
        // when
        when(storeRepository.findById(any())).thenReturn(java.util.Optional.of(STORE));
        when(productRepository.findById(any())).thenReturn(Optional.of(AVAILABLE_PROD));
        // given
        List<ProductWithId> result = purchaseService.makeAnPurchase(CORRECT_PURCHASE);
        // than
        assertEquals(PURCHASED_PRODS, result.get(0));
        verify(storeRepository, times(1)).findById(anyInt());
        verify(productRepository, times(1)).findById(anyInt());
        verify(purchaseRepository, times(1)).save(any());
        verify(productRepository, times(1)).saveAll(any());
        verify(productRepository, times(0)).delete(any());
    }

    @Test
    void makeAnPurchase_ProductEndTest() {
        // when
        when(storeRepository.findById(any())).thenReturn(java.util.Optional.of(STORE));
        when(productRepository.findById(any())).thenReturn(Optional.of(AVAILABLE_PROD_THAN_END));
        // given
        List<ProductWithId> result = purchaseService.makeAnPurchase(PURCHASE_PRODUCT_END);
        assertEquals(PURCHASED_PRODS, result.get(0));
        // than
        verify(storeRepository, times(1)).findById(anyInt());
        verify(productRepository, times(1)).findById(anyInt());
        verify(purchaseRepository, times(1)).save(any());
        verify(productRepository, times(1)).saveAll(any());
        verify(productRepository, times(1)).delete(any());
    }

    @Test
    void makeAnPurchase_LowerThanAvailableTest() {
        // when
        when(storeRepository.findById(any())).thenReturn(java.util.Optional.of(STORE));
        when(productRepository.findById(any())).thenReturn(Optional.of(AVAILABLE_PROD));
        // given
        assertThrows(LowerThanAvaibleException.class, () -> purchaseService.makeAnPurchase(PURCHASE_LOWER_THAN_AVAL));
        // than
        verify(storeRepository, times(1)).findById(anyInt());
        verify(productRepository, times(1)).findById(anyInt());
        verify(purchaseRepository, times(0)).save(any());
        verify(productRepository, times(0)).saveAll(any());
        verify(productRepository, times(0)).delete(any());
    }

   @Test
    void makeAnPurchase_ProductNotFoundTest() {
        when(storeRepository.findById(any())).thenReturn(java.util.Optional.of(STORE));
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> purchaseService.makeAnPurchase(PURCHASE));
        verify(storeRepository, times(1)).findById(anyInt());
        verify(productRepository, times(1)).findById(anyInt());
    }

    @Test
    void makeAnPurchase_InvalidStoreIdTest() {
        assertThrows(InvalidPropertyException.class, () -> purchaseService.makeAnPurchase(PURCHASE_INVALID_STOREID));
        verifyNullTimes();
    }

    @Test
    void makeAnPurchase_InvalidAmountOfProductTest() {
        // when
        assertThrows(InvalidPropertyException.class, () -> purchaseService.makeAnPurchase(PURCHASE_INVALID_AMOUNT));
        verifyNullTimes();

    }

    @Test
    void makeAnPurchase_InvalidIdOfProductTest() {
        // when
        assertThrows(InvalidPropertyException.class, () -> purchaseService.makeAnPurchase(PURCHASE_INVALID_ID));
        verifyNullTimes();
    }

    private void verifyNullTimes(){
        verify(storeRepository, times(0)).findById(anyInt());
        verify(productRepository, times(0)).findById(anyInt());
        verify(purchaseRepository, times(0)).save(any());
        verify(productRepository, times(0)).saveAll(any());
        verify(productRepository, times(0)).delete(any());
    }
}