package com.simbirsoft.internship.to.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbirsoft.internship.to.product.ProductWithId;

import java.util.HashSet;
import java.util.Set;

public class CategoryWithProducts extends CategoryWithId{
    @JsonProperty("products")
    private Set<ProductWithId> products = new HashSet<>();

    public CategoryWithProducts() {
    }

    public CategoryWithProducts(Integer id, String name, Set<ProductWithId> productWithIds, Integer amountOfProducts) {
        super(id, name, amountOfProducts);
        this.products = productWithIds;
    }

    public Set<ProductWithId> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductWithId> products) {
        this.products = products;
    }
}
