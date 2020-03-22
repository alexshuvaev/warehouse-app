package com.simbirsoft.internship.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public String toString() {
        return super.toString() + "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductWithId that = (ProductWithId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
