package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.WriteOffProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteOffProductRepisitory extends JpaRepository<WriteOffProductEntity, Integer> {
}
