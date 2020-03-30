package com.simbirsoft.internship.entity;

import javax.persistence.*;

@Entity
@Table(name = "writeoff_product")
public class WriteOffProductEntity extends AbstractNamedEntity {
    @Column(name = "description")
    private String description;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private int amount;

    @Column(name = "category_id")
    private int categoryId;

    @ManyToOne
    @JoinColumn(name = "writeoff_id")
    private WriteOffEntity writeOff;

    public WriteOffProductEntity() {
    }

    public WriteOffProductEntity(Integer id, String name) {
        super(id, name);
    }

    public WriteOffProductEntity(Integer id, String name, String description, int productId, int categoryId, double price, int amount, WriteOffEntity writeOff) {
        this(id, name);
        this.description = description;
        this.productId = productId;
        this.categoryId = categoryId;
        this.price = price;
        this.amount = amount;
        this.writeOff = writeOff;
    }

    public String getDescription() {
        return description;
    }

    public int getProductId() {
        return productId;
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

    public WriteOffEntity getWriteOff() {
        return writeOff;
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
        return super.toString() + " description=" + description + " productId=" + productId + " price=" + price + " amount=" + amount + " categoryId=" + categoryId + " writeOffId=" + writeOff.getId();
    }
}
