package com.simbirsoft.internship.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "writeoff")
public class WriteOffEntity extends AbstractDateTimeEntity {
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

    public Set<WriteOffProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<WriteOffProductEntity> productEntitySet) {
        this.products = productEntitySet;
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
}
