package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer>, GlobalSequence {
}
