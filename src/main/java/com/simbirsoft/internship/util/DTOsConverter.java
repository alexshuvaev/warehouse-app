package com.simbirsoft.internship.util;

import com.simbirsoft.internship.entity.*;
import com.simbirsoft.internship.dto.*;
import com.simbirsoft.internship.dto.category.CategoryId;
import com.simbirsoft.internship.dto.category.CategoryIdProducts;
import com.simbirsoft.internship.dto.product.ProductDescId;

import java.util.*;
import java.util.stream.Collectors;

public final class DTOsConverter {
    private DTOsConverter() {
    }

    /**
     * Category entity < > Category DTOs converters
     */
    public static List<CategoryId> categoryWithIdListCreate(List<CategoryEntity> categoryEntityList) {
        return categoryEntityList.stream()
                .map(e -> new CategoryId(
                        e.getName(),
                        e.getId(),
                        e.getProducts().size()))
                .collect(Collectors.toList());
    }

    public static CategoryIdProducts categoryWithProductsCreate(CategoryEntity category) {
        return new CategoryIdProducts(
                category.getId(),
                category.getName(),
                productWithIdSetCreate(category),
                category.getProducts().size());
    }

    public static CategoryId categoryWithIdCreate(CategoryEntity category) {
        return new CategoryId(category.getName(), category.getId(), 0);
    }

    /**
     * Product entity < > Product DTOs converters
     */
    private static Set<ProductDescId> productWithIdSetCreate(CategoryEntity category) {
        return category.getProducts().stream()
                .map(DTOsConverter::productWithIdCreate)
                .collect(Collectors.toSet());
    }

    public static ProductDescId productWithIdCreate(ProductEntity product) {
        return new ProductDescId(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount(),
                product.getCategory().getId(),
                product.getId());
    }

    public static List<ProductDescId> productsListCreate(List<ProductEntity> productEntityList) {
        return productEntityList.stream()
                .map(DTOsConverter::productCreate)
                .collect(Collectors.toList());
    }

    public static ProductDescId productCreate(ProductEntity product) {
        return new ProductDescId(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount(),
                product.getCategory().getId(),
                product.getId());
    }

    /**
     * WriteOff entity < > WriteOff DTOs converters
     */
    public static Set<WriteOffProductEntity> writeOffProductEntitySetCreate(List<ProductEntity> productsFromWriteOff,
                                                                            Map<Integer, Integer> map,
                                                                            WriteOffEntity writeOffEntity) {
        return productsFromWriteOff.stream()
                .map(productEntity -> writeOffProductEntityCreate(productEntity, map, writeOffEntity))
                .collect(Collectors.toSet());
    }

    public static WriteOffProductEntity writeOffProductEntityCreate(ProductEntity productEntity,
                                                                    Map<Integer, Integer> map,
                                                                    WriteOffEntity writeOffEntity) {
        return new WriteOffProductEntity(
                null,
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getId(),
                productEntity.getCategory().getId(),
                productEntity.getPrice(),
                map.get(productEntity.getId()),
                writeOffEntity);
    }

    public static List<ConfirmedWriteOff> writeOffToConfirmListCreate(List<WriteOffEntity> writeOffEntityList) {
        return writeOffEntityList.stream()
                .map(DTOsConverter::writeOffToConfirmCreate)
                .collect(Collectors.toList());
    }

    public static ConfirmedWriteOff writeOffToConfirmCreate(WriteOffEntity writeOffEntity) {
        return new ConfirmedWriteOff(
                writeOffEntity.getId(),
                writeOffEntity.getDateTime(),
                productWithIdListCreate(writeOffEntity.getProducts()),
                writeOffEntity.getTotalPrice(),
                writeOffEntity.isConfirm());
    }

    public static List<ProductDescId> productWithIdListCreate(Set<WriteOffProductEntity> productEntityList) {
        return productEntityList.stream()
                .map(DTOsConverter::productWithIdForWriteOffListCreate)
                .collect(Collectors.toList());
    }

    private static ProductDescId productWithIdForWriteOffListCreate(WriteOffProductEntity writeOffProductEntity) {
        return new ProductDescId(
                writeOffProductEntity.getName(),
                writeOffProductEntity.getDescription(),
                writeOffProductEntity.getPrice(),
                writeOffProductEntity.getAmount(),
                writeOffProductEntity.getCategoryId(),
                writeOffProductEntity.getProductId()
        );
    }

    /**
     * Product entity < > Purchase Product entity converters
     */
    public static Set<PurchaseProductEntity> purchaseProductEntitySetCreate(List<ProductEntity> productEntities,
                                                                            Map<Integer, Integer> map,
                                                                            PurchaseEntity purchaseEntity) {
        return productEntities.stream()
                .map(productEntity -> purchaseProductEntityCreate(productEntity, map, purchaseEntity))
                .collect(Collectors.toSet());
    }

    public static PurchaseProductEntity purchaseProductEntityCreate(ProductEntity productEntity,
                                                                    Map<Integer, Integer> map,
                                                                    PurchaseEntity purchaseEntity) {
        return new PurchaseProductEntity(
                null,
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                map.get(productEntity.getId()),
                productEntity.getCategory().getId(),
                purchaseEntity);
    }

    public static ConfirmedPurchase purchaseToConfirmCreate(PurchaseEntity purchaseEntity) {
        return new ConfirmedPurchase(
                purchaseEntity.getId(),
                purchaseEntity.getDateTime(),
                purchasedProductWithIdListCreate(purchaseEntity.getProducts()),
                purchaseEntity.getTotalPrice());
    }

    public static List<ProductDescId> purchasedProductWithIdListCreate(Set<PurchaseProductEntity> productEntityList) {
        return productEntityList.stream()
                .map(DTOsConverter::productWithIdForPurchaseListCreate)
                .collect(Collectors.toList());
    }

    private static ProductDescId productWithIdForPurchaseListCreate(PurchaseProductEntity purchaseProductEntity) {
        return new ProductDescId(
                purchaseProductEntity.getName(),
                purchaseProductEntity.getDescription(),
                purchaseProductEntity.getPrice(),
                purchaseProductEntity.getAmount(),
                purchaseProductEntity.getCategoryId(),
                purchaseProductEntity.getId()
        );
    }
}
