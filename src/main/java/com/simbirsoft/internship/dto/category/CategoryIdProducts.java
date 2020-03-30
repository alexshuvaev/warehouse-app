package com.simbirsoft.internship.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbirsoft.internship.dto.product.ProductDescId;

import java.util.HashSet;
import java.util.Set;

public class CategoryIdProducts extends CategoryId {
    @JsonProperty("products")
    private Set<ProductDescId> products = new HashSet<>();

    public CategoryIdProducts() {
    }

    public CategoryIdProducts(Integer id, String name, Set<ProductDescId> productWithIds, Integer amountOfProducts) {
        super(name, id, amountOfProducts);
        this.products = productWithIds;
    }

    public Set<ProductDescId> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDescId> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return super.toString() + " products=" + products;
    }
}
