package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.to.product.Product;
import com.simbirsoft.internship.util.exception.MustBeUniqueException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductEntity findById(int id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found Product with id=" + id));
    }

    @Override
    public List<ProductEntity> findAllById(Set<Integer> ids) {
        List<ProductEntity> products = productRepository.findAllById(ids);
        Set<Integer> foundedIds;
        if (products.size() < ids.size()) {
            foundedIds = products.stream()
                    .map(ProductEntity::getId)
                    .collect(Collectors.toSet());
            ids.removeAll(foundedIds);
            throw new NotFoundException("Not found Products with ids=" + ids.toString());
        }
        return products;
    }

    @Transactional
    @Override
    public List<ProductEntity> createList(List<Product> productList) {
        return productRepository.saveAll(
                productList.stream()
                        .map(e -> {
                            int categoryId = e.getCategoryId();
                            return new ProductEntity(null,
                                    checkUniqueName(e.getName()),
                                    e.getDescription(),
                                    e.getPrice(),
                                    e.getAmount(),
                                    categoryRepository.findById(categoryId).orElseThrow(
                                            () -> new NotFoundException("Not found Category with id=" + categoryId)));
                        }).collect(Collectors.toList()));
    }

    private String checkUniqueName(String name) {
        if (productRepository.existsByName(name)) {
            throw new MustBeUniqueException("Product with name: " + name + " already exist (name must be unique)");
        }
        return name;
    }
}
