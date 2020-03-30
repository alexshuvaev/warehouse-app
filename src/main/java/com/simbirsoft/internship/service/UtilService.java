package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
import com.simbirsoft.internship.util.exception.LowerThanAvaibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UtilService {
    private ProductRepository productRepository;

    @Autowired
    public UtilService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void availabilityCheckAndCommit(List<ProductEntity> products, Map<Integer, Integer> map){
        List<ProductEntity> updatedProducts = new ArrayList<>();
        List<ProductEntity> endedProducts = new ArrayList<>();
        products.forEach(e-> {
            e.setAmount(e.getAmount() - map.get(e.getId()));
            if (e.getAmount() >= 0) {
                updatedProducts.add(e);
            }
            if (e.getAmount() == 0) {
                endedProducts.add(e);
            }
            if (e.getAmount() < 0) {
                throw new LowerThanAvaibleException("Product availability is lower than in the Purchase. Product id=" + e.getId());
            }
        });
        if (!updatedProducts.isEmpty()){
            productRepository.saveAll(updatedProducts);
        }
        if (!endedProducts.isEmpty()){
            productRepository.deleteAll(endedProducts);
        }
    }

    public void validation(Integer idOfProduct, int amountOfProduct) {
        if (idOfProduct <= 0) {
            throw new InvalidPropertyException("Product id must be positive number.");
        }
        if (amountOfProduct <= 0){
            throw new InvalidPropertyException("Amount of product must be positive number.");
        }
    }
}
