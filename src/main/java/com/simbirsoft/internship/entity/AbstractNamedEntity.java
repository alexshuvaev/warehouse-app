package com.simbirsoft.internship.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public AbstractNamedEntity() {
    }

    public AbstractNamedEntity(Integer id, String name) {
       super(id);
       this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return '(' + name + ')';
    }
}
