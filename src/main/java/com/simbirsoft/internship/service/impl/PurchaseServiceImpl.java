package com.simbirsoft.internship.service.impl;

import com.simbirsoft.internship.dto.Position;
import com.simbirsoft.internship.dto.Purchase;
import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.entity.PurchaseEntity;
import com.simbirsoft.internship.entity.PurchaseProductEntity;
import com.simbirsoft.internship.entity.StoreEntity;
import com.simbirsoft.internship.repository.PurchaseRepository;
import com.simbirsoft.internship.repository.StoreRepository;
import com.simbirsoft.internship.service.ProductService;
import com.simbirsoft.internship.service.PurchaseService;
import com.simbirsoft.internship.service.UtilService;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.simbirsoft.internship.util.DTOsConverter.purchaseProductEntitySetCreate;

@Service
@Transactional(readOnly = true)
public class PurchaseServiceImpl implements PurchaseService {
    private PurchaseRepository purchaseRepository;
    private ProductService productService;
    private StoreRepository storeRepository;

    private UtilService utilService;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductService productService, StoreRepository storeRepository, UtilService utilService) {
        this.purchaseRepository = purchaseRepository;
        this.productService = productService;
        this.storeRepository = storeRepository;
        this.utilService = utilService;
    }

    @Transactional
    @Override
    public PurchaseEntity makeAnPurchase(Purchase purchase) {
        int storeId = purchase.getStoreId();

        if (storeId <= 0) throw new InvalidPropertyException("The Store id can't be 0 or negative number.");
        StoreEntity store = storeRepository.findById(purchase.getStoreId()).orElseThrow(
                () -> new NotFoundException("Not found Store with id=" + purchase.getStoreId()));

        PurchaseEntity purchaseEntity = new PurchaseEntity(null, store, Collections.emptySet(), 0);

        Map<Integer, Integer> map = purchase.getPositions().stream()
                .peek(e-> utilService.validation(e.getIdOfProduct(), e.getAmountOfProduct()))
                .collect(Collectors.toMap(Position::getIdOfProduct, Position::getAmountOfProduct));

        List<ProductEntity> products = productService.findAllById(map.keySet());
        Set<PurchaseProductEntity> purchaseProductEntities = purchaseProductEntitySetCreate(products, map, purchaseEntity);

        purchaseEntity.setProducts(purchaseProductEntities);
        purchaseEntity.setTotalPrice(sum(purchaseProductEntities));

        utilService.availabilityCheckAndCommit(products, map);

        purchaseRepository.save(purchaseEntity);
        return purchaseEntity;
    }

    private double sum(Collection<PurchaseProductEntity> products) {
        return products.stream()
                .map(e -> e.getPrice()*e.getAmount()).collect(Collectors.toList()).stream()
                .mapToDouble(Double::doubleValue).sum();
    }
}
