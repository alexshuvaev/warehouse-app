package com.simbirsoft.internship.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + " address=" + address + " purchases.size=" + purchases.size();
    }
}

