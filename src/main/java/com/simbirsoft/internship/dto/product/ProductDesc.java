package com.simbirsoft.internship.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDesc extends Product {
    @JsonProperty("product_description")
    private String description;

    public ProductDesc() {
    }

    public ProductDesc(String name, String description, double price, int amount, int categoryId) {
    super(name, price, amount, categoryId);
    this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return " name=" + getName() + " price=" + getPrice() + " amount=" + getAmount() + " categoryId=" + getCategoryId();
    }
}
