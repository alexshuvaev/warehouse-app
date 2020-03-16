package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.to.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.simbirsoft.internship.util.ValidationUtil.*;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductEntity findById(int id) {
        return checkNotFoundWithId(productRepository.findById(id).orElse(null), id);
    }

    @Override
    public List<ProductEntity> findAllById(Set<Integer> ids) {
        return checkNotFoundWithList(productRepository.findAllById(ids), ids);
    }

    @Transactional
    @Override
    public List<ProductEntity> createList(List<Product> productList) {
        return productRepository.saveAll(
                productList.stream()
                        .map(e ->
                                new ProductEntity(null,
                                        checkUniqueName(productRepository.existsByName(e.getName()), e).getName(),
                                        e.getDescription(),
                                        e.getPrice(),
                                        e.getAmount(),
                                        checkNotFoundWithId(categoryRepository.findById(e.getCategoryId()).orElse(null), e.getCategoryId())
                                ))
                        .collect(Collectors.toList())
        );
    }
}
