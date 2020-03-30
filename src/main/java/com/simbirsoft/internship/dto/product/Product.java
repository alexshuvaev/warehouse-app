package com.simbirsoft.internship.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    @JsonProperty("product_name")
    private String name;

    @JsonProperty("price")
    private double price;

    @JsonProperty("amount_of_product")
    private Integer amount;

    @JsonProperty("category_id")
    private Integer categoryId;

    public Product() {
    }

    public Product(String name, double price, Integer amount, Integer categoryId) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return " name=" + name + " price=" + price + " amount=" + amount + " categoryId=" + categoryId;
    }
}