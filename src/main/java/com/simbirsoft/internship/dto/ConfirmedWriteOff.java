package com.simbirsoft.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbirsoft.internship.dto.product.ProductDescId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConfirmedWriteOff {
    @JsonProperty("write_off_id")
    private int id;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @JsonProperty("total_price")
    private double totalPrice;

    @JsonProperty("is_confirm")
    private boolean confirm;

    @JsonProperty("products")
    private List<ProductDescId> products = new ArrayList<>();

    public ConfirmedWriteOff() {
    }

    public ConfirmedWriteOff(int id, LocalDateTime dateTime, List<ProductDescId> products, double totalPrice, boolean confirm) {
        this.id = id;
        this.dateTime = dateTime;
        this.products = products;
        this.totalPrice = totalPrice;
        this.confirm = confirm;
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

    public boolean isConfirm() {
        return confirm;
    }

    @Override
    public String toString() {
        return "id=" + id + " dateTime=" + dateTime + " Products=" + products + " totalPrice=" + totalPrice + " isConfirm=" + confirm;
    }
}



