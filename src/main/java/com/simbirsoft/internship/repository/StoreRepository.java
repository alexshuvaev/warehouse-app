package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Integer>, GlobalSequence {
}
