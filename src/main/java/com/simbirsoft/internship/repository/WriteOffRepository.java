package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.WriteOffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WriteOffRepository extends JpaRepository<WriteOffEntity, Integer> {
    /**
     * Get all WriteOff entities.
     *
     * @param startDate generate report from startDate.
     * @param endDate generate report up to endDate.
     * @return list of WriteOff entities.
     */
    List<WriteOffEntity> getAllBetween(LocalDateTime startDate, LocalDateTime endDate);
}
