package com.simbirsoft.internship.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "purchase_product",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "purchase_id")})
    private Set<PurchaseEntity> purchases = new HashSet<>();

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

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", category=" + category +
                ", purchases=" + purchases +
                '}';
    }
}
