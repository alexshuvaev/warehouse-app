package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
    @Query("SELECT p.store.id, p.totalPrice FROM PurchaseEntity p WHERE p.dateTime >= :start AND p.dateTime < :end")
    List<Tuple> getMainKpi(LocalDateTime start, LocalDateTime end);

/*    // WHERE p.dateTime >= :start AND p.dateTime < :end
    @Query("SELECT p.products FROM PurchaseEntity p JOIN FETCH ProductEntity e")
    List<Tuple> getPurchasesProducts(LocalDateTime start, LocalDateTime end);*/
}
