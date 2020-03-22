package com.simbirsoft.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbirsoft.internship.dto.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    @JsonProperty("products")
    private List<Product> products = new ArrayList<>();

    public Invoice() {
    }
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
