package com.simbirsoft.internship.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="store")
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

    public void setAddress(String address) {
        this.address = address;
    }

   public Set<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<PurchaseEntity> orderSet) {
        this.purchases = orderSet;
    }
}

