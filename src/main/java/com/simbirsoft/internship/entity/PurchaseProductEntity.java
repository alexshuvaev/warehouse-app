package com.simbirsoft.internship.entity;

import javax.persistence.*;

@Entity
@Table(name = "purchase_product")
public class PurchaseProductEntity extends AbstractNamedEntity {
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private int amount;

    @Column(name = "category_id")
    private int categoryId;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private PurchaseEntity purchase;

    public PurchaseProductEntity() {
    }

    public PurchaseProductEntity(Integer id, String name) {
        super(id, name);
    }

    public PurchaseProductEntity(Integer id, String name, String description, double price, int amount, int categoryId, PurchaseEntity purchaseEntity) {
        this(id, name);
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.categoryId = categoryId;
        this.purchase = purchaseEntity;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public PurchaseEntity getPurchase() {
        return purchase;
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
        return super.toString() + " price=" + price + " amount=" + amount + " categoryId=" + categoryId + " purchaseId=" + purchase.getId();
    }
}
