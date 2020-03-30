package com.simbirsoft.internship.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ProductForReport {
    @JsonProperty("product_name")
    private String name;

    @JsonProperty("price_for_unit")
    private double priceForUnit;

    @JsonProperty("amount_of_product")
    private Integer amount;

    @JsonProperty("total_price")
    private double totalPrice;

    public ProductForReport() {
    }

    public ProductForReport(String name, double priceForUnit, Integer amount, double totalPrice) {
        this.name = name;
        this.priceForUnit = priceForUnit;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public double getPriceForUnit() {
        return priceForUnit;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductForReport)) return false;
        ProductForReport that = (ProductForReport) o;
        return Double.compare(that.getPriceForUnit(), getPriceForUnit()) == 0 &&
                Double.compare(that.totalPrice, totalPrice) == 0 &&
                name.equals(that.name) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getPriceForUnit(), getAmount(), totalPrice);
    }

    @Override
    public String toString() {
        return " name=" + name + " priceForUnit=" + priceForUnit +  " amount=" + amount + " totalPrice=" + totalPrice;
    }
}
