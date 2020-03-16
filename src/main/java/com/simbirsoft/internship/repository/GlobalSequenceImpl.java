package com.simbirsoft.internship.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional(readOnly = true)
public class GlobalSequenceImpl implements GlobalSequence {
    @PersistenceContext
    private EntityManager em;

    public Integer genNextId() {
        Query q = em.createNativeQuery("SELECT nextval('global_seq')");
        return Integer.parseInt(q.getSingleResult().toString());
    }
}
