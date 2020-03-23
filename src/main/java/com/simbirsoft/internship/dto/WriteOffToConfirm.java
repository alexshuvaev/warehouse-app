package com.simbirsoft.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbirsoft.internship.dto.product.ProductWithId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WriteOffToConfirm {
    @JsonProperty("write_off_id")
    private int id;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @JsonProperty("products")
    private List<ProductWithId> products = new ArrayList<>();

    @JsonProperty("total_price")
    private double totalPrice;

    @JsonProperty("is_confirm")
    private boolean confirm;

    public WriteOffToConfirm() {
    }

    public WriteOffToConfirm(int id, LocalDateTime dateTime, List<ProductWithId> products, double totalPrice, boolean confirm) {
        this.id = id;
        this.dateTime = dateTime;
        this.products = products;
        this.totalPrice = totalPrice;
        this.confirm = confirm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ProductWithId> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithId> products) {
        this.products = products;
    }
}



