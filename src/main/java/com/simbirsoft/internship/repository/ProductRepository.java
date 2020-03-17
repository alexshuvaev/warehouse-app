package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    /**
     * Validation by name. Product name must be unique.
     *
     * @param name of Product.
     * @return true if Product with this name exist, or false if not.
     */
    boolean existsByName(String name);
}
