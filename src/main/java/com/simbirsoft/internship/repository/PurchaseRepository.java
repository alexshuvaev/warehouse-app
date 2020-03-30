package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
    /**
     * Get all Purchases from DB.
     *
     * @param startDate generate report from startDate.
     * @param endDate generate report up to endDate.
     * @return list of purchase entities.
     */
    List<PurchaseEntity> getAllBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get data from different columns of several tables for store sales report.
     *
     * @param storeId Store id.
     * @param startDate generate report from startDate.
     * @param endDate generate report up to endDate.
     * @return list of rows from DT. see SQL query in PurchaseEntity.getSoldPurchases.
     */
    List<Tuple> getSoldPurchases(Integer storeId, LocalDateTime startDate, LocalDateTime endDate);
}
