package com.simbirsoft.internship.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "store")
public class StoreEntity extends AbstractNamedEntity {
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "store")
    private Set<PurchaseEntity> purchases = new HashSet<>();

    public StoreEntity() {
    }

    public StoreEntity(Integer id, String name, String address, Set<PurchaseEntity> purchases) {
        super(id, name);
        this.address = address;
        this.purchases = purchases;
    }

    public String getAddress() {
        return address;
    }

    public Set<PurchaseEntity> getPurchases() {
        return purchases;
    }
}

