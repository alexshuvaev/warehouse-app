package com.simbirsoft.internship.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ProductDescId extends ProductDesc {
    @JsonProperty("product_id")
    private int id;

    public ProductDescId() {
    }

    public ProductDescId(String name, String description, double price, int amount, int categoryId, int id) {
        super(name, description, price, amount, categoryId);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDescId that = (ProductDescId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "id=" + id + " " + super.toString();
    }
}
