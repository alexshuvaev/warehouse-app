package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    /**
     * Find Product in DB by name.
     *
     * @param name of Product.
     * @return ProductEntity if exist. Null - if not.
     */
    Optional<ProductEntity> findByName(String name);
}
