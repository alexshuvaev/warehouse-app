package com.simbirsoft.internship.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NamedQuery(name = WriteOffEntity.GET_ALL_BETWEEN,
        query = "SELECT w FROM WriteOffEntity w " +
                "WHERE w.dateTime >= :startDate AND w.dateTime < :endDate " +
                "AND w.confirm = true " +
                "ORDER BY w.dateTime DESC")
@Entity
@Table(name = "writeoff")
public class WriteOffEntity extends AbstractDateTimeEntity {
    public static final String GET_ALL_BETWEEN = "WriteOffEntity.getAllBetween";
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "writeOff", cascade = CascadeType.ALL)
    private Set<WriteOffProductEntity> products = new HashSet<>();

    @Column(name = "confirm")
    private boolean confirm;

    @Column(name = "total_price")
    private double totalPrice;


    public WriteOffEntity() {
    }

    public WriteOffEntity(Integer id, Set<WriteOffProductEntity> products, boolean confirm) {
        super(id);
        this.products = products;
        this.confirm = confirm;
    }

    public WriteOffEntity(Integer id, Set<WriteOffProductEntity> products, boolean confirm, double totalPrice) {
        this(id, products, confirm);
        this.totalPrice = totalPrice;
    }

    public Set<WriteOffProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<WriteOffProductEntity> products) {
        this.products = products;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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
        return super.toString() + " Products=" + products + " isConfirm=" + confirm + " totalPrice=" + totalPrice;
    }
}
