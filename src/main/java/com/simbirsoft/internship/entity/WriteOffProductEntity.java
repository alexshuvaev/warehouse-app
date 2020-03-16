package com.simbirsoft.internship.entity;

import javax.persistence.*;

@Entity
@Table(name = "writeoff_product")
public class WriteOffProductEntity extends AbstractNamedEntity {
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

    public WriteOffProductEntity(Integer id, String name, int productId, int categoryId, double price, int amount, WriteOffEntity writeOff) {
        this(id, name);
        this.productId = productId;
        this.categoryId = categoryId;
        this.price = price;
        this.amount = amount;
        this.writeOff = writeOff;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "WriteOffProductEntity{" +
                "productId=" + productId +
                ", price=" + price +
                ", amount=" + amount +
                ", categoryId=" + categoryId +
                ", writeOff=" + writeOff +
                '}';
    }
}
