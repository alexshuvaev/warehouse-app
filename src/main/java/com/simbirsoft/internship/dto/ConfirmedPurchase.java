package com.simbirsoft.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbirsoft.internship.dto.product.ProductDescId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConfirmedPurchase {
    @JsonProperty("purchase_id")
    private int id;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @JsonProperty("total_price")
    private double totalPrice;

    @JsonProperty("products")
    private List<ProductDescId> products = new ArrayList<>();

    public ConfirmedPurchase() {
    }

    public ConfirmedPurchase(int id, LocalDateTime dateTime, List<ProductDescId> products, double totalPrice) {
        this.id = id;
        this.dateTime = dateTime;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<ProductDescId> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "id=" + id + " dateTime=" + dateTime + " products=" + products + " totalPrice=" + totalPrice;
    }
}



