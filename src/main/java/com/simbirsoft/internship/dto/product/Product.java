package com.simbirsoft.internship.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    @JsonProperty("product_name")
    private String name;

    @JsonProperty("product_description")
    private String description;

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

    public Product(String name, String description, double price, Integer amount, Integer categoryId) {
        this(name, price, amount, categoryId);
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}