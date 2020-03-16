package com.simbirsoft.internship.to.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductWithId extends Product {
    @JsonProperty("product_id")
    private int id;

    public ProductWithId() {
    }

    public ProductWithId(int id, String name, String description, double price, int amount, int categoryId) {
        super(name, description, price, amount, categoryId);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
