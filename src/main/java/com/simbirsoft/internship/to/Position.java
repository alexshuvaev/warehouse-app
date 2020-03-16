package com.simbirsoft.internship.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    @JsonProperty("id")
    private Integer idOfProduct;

    @JsonProperty("amount")
    private Integer amountOfProduct;

    public Position() {
    }

    public Position(Integer idOfProduct, Integer amountOfProduct) {
        this.idOfProduct = idOfProduct;
        this.amountOfProduct = amountOfProduct;
    }

    public Integer getIdOfProduct() {
        return idOfProduct;
    }

    public void setIdOfProduct(Integer idOfProduct) {
        this.idOfProduct = idOfProduct;
    }

    public Integer getAmountOfProduct() {
        return amountOfProduct;
    }

    public void setAmountOfProduct(Integer amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }
}