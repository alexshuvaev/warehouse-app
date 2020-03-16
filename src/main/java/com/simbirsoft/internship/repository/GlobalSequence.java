package com.simbirsoft.internship.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSequence {
    /**
     * Get next id.
     *
     * @return next id from global sequences.
     */
    Integer genNextId();
}
