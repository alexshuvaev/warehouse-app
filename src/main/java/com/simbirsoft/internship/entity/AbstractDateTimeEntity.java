package com.simbirsoft.internship.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractDateTimeEntity extends AbstractBaseEntity {
    @Column(name = "date_time", unique = true, columnDefinition = "timestamp default now()")
    private LocalDateTime dateTime;

    public AbstractDateTimeEntity() {
    }

    public AbstractDateTimeEntity(Integer id) {
        super(id);
        this.dateTime = LocalDateTime.now();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
