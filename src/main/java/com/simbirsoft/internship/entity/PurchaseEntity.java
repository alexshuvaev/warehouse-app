package com.simbirsoft.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NamedQuery(name = PurchaseEntity.GET_ALL_BETWEEN,
        query = "SELECT p FROM PurchaseEntity p " +
                "WHERE p.dateTime >= :startDate AND p.dateTime <= :endDate " +
                "ORDER BY p.dateTime DESC")
@NamedQuery(name = PurchaseEntity.GET_SOLD,
        query = "SELECT prod.name as name, SUM(prod.amount) as amount, SUM(prod.price) as price, prod.categoryId as cat " +
                "FROM PurchaseProductEntity prod JOIN FETCH PurchaseEntity purch " +
                "ON prod.purchase.id = purch.id " +
                "WHERE purch.store.id = :storeId " +
                "AND (purch.dateTime >= :startDate AND purch.dateTime <= :endDate) " +
                "GROUP BY name, purch.store.id, cat " +
                "ORDER BY name, amount DESC"
)
@Entity
@Table(name = "purchase")
public class PurchaseEntity extends AbstractDateTimeEntity {
    public static final String GET_ALL_BETWEEN = "PurchaseEntity.getAllBetween";
    public static final String GET_SOLD = "PurchaseEntity.getSoldPurchases";

    @Column(name = "total_price")
    private double totalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "purchase", cascade = CascadeType.ALL)
    private Set<PurchaseProductEntity> products = new HashSet<>();

    public PurchaseEntity() {
    }

    public PurchaseEntity(Integer id, StoreEntity store, Set<PurchaseProductEntity> products, double totalPrice) {
        super(id);
        this.store = store;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public StoreEntity getStore() {
        return store;
    }

    public Set<PurchaseProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<PurchaseProductEntity> products) {
        this.products = products;
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
        return super.toString() + " totalPrice=" + totalPrice + " storeId=" + store.getId() + " Products=" + products;
    }
}
