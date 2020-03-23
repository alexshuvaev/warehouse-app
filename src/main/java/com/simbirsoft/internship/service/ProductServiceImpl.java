package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.repository.CategoryRepository;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.dto.product.Product;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
                        .map(this::createUpdateProductEntity).collect(Collectors.toList()));
    }

    private ProductEntity createUpdateProductEntity(Product productEntity) {
        productValidation(productEntity);

        int categoryId = productEntity.getCategoryId();
        String name = productEntity.getName();

        Optional<ProductEntity> product = productRepository.findByName(name);
        if (product.isPresent()) {
            ProductEntity p = product.get();
            p.setAmount(p.getAmount() + productEntity.getAmount());
            return p;
        } else {
            return new ProductEntity(null,
                    name,
                    productEntity.getDescription(),
                    productEntity.getPrice(),
                    productEntity.getAmount(),
                    categoryRepository.findById(categoryId).orElseThrow(
                            () -> new NotFoundException("Not found Category with id=" + categoryId)));
        }
    }

    /**
     * Product validation
     **/

    private void productValidation(Product product) {
        nameValidation(product.getName());
        priceValidation(product.getPrice());
        amountValidation(product.getAmount());
        descriptionValidation(product.getDescription());
        categoryValidation(product.getCategoryId());
    }

    private void nameValidation(String name) {
        // https://stackoverflow.com/a/44232658/12890862
        if (name.trim().isEmpty() || name.length() < 2 || name.length() > 60 || !name.matches("[\\p{L}\\d\\s.]+")) {
            throw new InvalidPropertyException("The Product name length must be between 2 and 60 chars, and no special characters.");
        }
    }

    private void priceValidation(double price) {
        if (price <= 0) {
            throw new InvalidPropertyException("The Product price must be positive number.");
        }
    }

    private void amountValidation(int amount) {
        if (amount < 0) {
            throw new InvalidPropertyException("The Product amount must be positive number or zero.");
        }
    }

    private void descriptionValidation(String description) {
        if (description.length() > 500) {
            throw new InvalidPropertyException("The Product description must be no more than 500 characters.");
        }
    }

    private void categoryValidation(int categoryId) {
        if (categoryId <= 0) {
            throw new InvalidPropertyException("The Product category id must be positive number.");
        }
    }
}
