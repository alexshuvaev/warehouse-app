package com.simbirsoft.internship.util;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.entity.WriteOffProductEntity;
import com.simbirsoft.internship.to.*;
import com.simbirsoft.internship.to.category.CategoryWithId;
import com.simbirsoft.internship.to.category.CategoryWithProducts;
import com.simbirsoft.internship.to.product.Product;
import com.simbirsoft.internship.to.product.ProductWithId;

import java.util.*;
import java.util.stream.Collectors;

public final class TosConverter {
    private TosConverter() {
    }

    /**
     * Category entity < > Category DTOs converters
     */
    public static List<CategoryWithId> categoryWithIdListCreate(List<CategoryEntity> categoryEntityList) {
        return categoryEntityList.stream()
                .map(e -> new CategoryWithId(
                        e.getId(),
                        e.getName(),
                        e.getProducts().size()))
                .collect(Collectors.toList());
    }

    public static CategoryWithProducts categoryWithProductsCreate(CategoryEntity category) {
        return new CategoryWithProducts(
                category.getId(),
                category.getName(),
                productWithIdSetCreate(category),
                category.getProducts().size());
    }

    public static CategoryWithId categoryWithIdCreate(CategoryEntity category) {
        return new CategoryWithId(category.getId(), category.getName());
    }

    /**
     * Product entity < > Product DTOs converters
     */
    private static Set<ProductWithId> productWithIdSetCreate(CategoryEntity category) {
        return category.getProducts().stream()
                .map(TosConverter::productWithIdCreate)
                .collect(Collectors.toSet());
    }

    public static ProductWithId productWithIdCreate(ProductEntity product) {
        return new ProductWithId(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount(),
                product.getCategory().getId());
    }

    public static List<Product> productsListCreate(List<ProductEntity> productEntityList) {
        return productEntityList.stream()
                .map(TosConverter::productCreate)
                .collect(Collectors.toList());
    }

    public static Product productCreate(ProductEntity product) {
        return product.isNew() ?
                new Product(
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getAmount(),
                        product.getCategory().getId()):
                new ProductWithId(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getAmount(),
                        product.getCategory().getId());
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
                productEntity.getId(),
                productEntity.getCategory().getId(),
                productEntity.getPrice(),
                map.get(productEntity.getId()),
                writeOffEntity);
    }

    public static List<WriteOffToConfirm> writeOffToConfirmListCreate(List<WriteOffEntity> writeOffEntityList) {
        return writeOffEntityList.stream()
                .map(TosConverter::writeOffToConfirmCreate)
                .collect(Collectors.toList());
    }

    public static WriteOffToConfirm writeOffToConfirmCreate(WriteOffEntity writeOffEntity) {
        return new WriteOffToConfirm(
                writeOffEntity.getId(),
                writeOffEntity.getDateTime(),
                productWithIdListCreate(writeOffEntity.getProducts()),
                writeOffEntity.getTotalPrice(),
                writeOffEntity.isConfirm());
    }

  public static List<ProductWithId> productWithIdListCreate(Set<WriteOffProductEntity> productEntityList) {
        return productEntityList.stream()
                .map(TosConverter::productWithIdForWriteOffListCreate)
                .collect(Collectors.toList());
    }

    private static ProductWithId productWithIdForWriteOffListCreate(WriteOffProductEntity writeOffProductEntity) {
        return new ProductWithId(
                writeOffProductEntity.getId(),
                writeOffProductEntity.getName(),
                "",
                writeOffProductEntity.getPrice(),
                writeOffProductEntity.getAmount(),
                writeOffProductEntity.getCategoryId()
        );
    }
}
