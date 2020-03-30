package com.simbirsoft.internship.entity;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class ProductEntity extends AbstractNamedEntity {
    @Column(name = "description")
    private String description;

    @Column(name="price")
    private double price;

    @Column(name = "amount")
    private int amount;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public ProductEntity() {
    }

    public ProductEntity(Integer id, String name) {
        super(id, name);
    }

    public ProductEntity(Integer id, String name, String description, double price, int amount, CategoryEntity category) {
        this(id, name);
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.category = category;
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CategoryEntity getCategory() {
        return category;
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
        return super.toString() + " description=" + description + " price=" + price + " amount=" + amount + " categoryId=" + category.getId();
    }
}
