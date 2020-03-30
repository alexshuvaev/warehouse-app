package com.simbirsoft.internship.service;

import com.simbirsoft.internship.dto.product.ProductDesc;
import com.simbirsoft.internship.entity.ProductEntity;

import java.util.List;
import java.util.Set;

public interface ProductService {
    /**
     * Get Product from DB by id.
     *
     * @param id of Product.
     * @return Product if not null, otherwise will be NotFoundException.
     */
    ProductEntity findById(int id);

    /**
     * Get Products list from DB by set of ids.
     *
     * @param ids set of Products ids.
     * @return Products list. If at least one Product can't be found by id, will be NotFoundException.
     */
    List<ProductEntity> findAllById(Set<Integer> ids);

    /**
     * Create Products list.
     *
     * @param productDescList list of new Products.
     * @return list of new saved in DB Products.
     * If ProductEntity name not unique will be InvalidPropertyException.
     * If Category not found by id (Category id must be provided in each Product) will be NotFoundException.
     */
    List<ProductEntity> createList(List<ProductDesc> productDescList);
}
