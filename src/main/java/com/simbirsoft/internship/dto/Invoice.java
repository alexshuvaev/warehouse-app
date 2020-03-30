package com.simbirsoft.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbirsoft.internship.dto.product.ProductDesc;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    @JsonProperty("products")
    private List<ProductDesc> products = new ArrayList<>();

    public Invoice() {
    }
    public List<ProductDesc> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Products=" + products;
    }
}
