package com.simbirsoft.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "purchase")
public class PurchaseEntity extends AbstractDateTimeEntity {
    @Column(name = "total_price")
    private double totalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "purchase_product",
            joinColumns = {@JoinColumn(name = "purchase_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private Set<ProductEntity> products = new HashSet<>();

    public PurchaseEntity() {
    }

    public PurchaseEntity(Integer id, StoreEntity store, Set<ProductEntity> products, double totalPrice) {
        super(id);
        this.store = store;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public StoreEntity getStore() {
        return store;
    }

    public void setProducts(Set<ProductEntity> productEntitySet) {
        this.products = productEntitySet;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }
}
