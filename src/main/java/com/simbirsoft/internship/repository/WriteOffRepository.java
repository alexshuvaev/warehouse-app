package com.simbirsoft.internship.repository;

import com.simbirsoft.internship.entity.WriteOffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteOffRepository extends JpaRepository<WriteOffEntity, Integer> {
}
