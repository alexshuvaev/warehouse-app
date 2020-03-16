package com.simbirsoft.internship.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    @JsonProperty("id")
    private int idOfProduct;

    @JsonProperty("amount")
    private int amountOfProduct;

    public Position() {
    }

    public Position(Integer idOfProduct, Integer amountOfProduct) {
        this.idOfProduct = idOfProduct;
        this.amountOfProduct = amountOfProduct;
    }

    public Integer getIdOfProduct() {
        return idOfProduct;
    }

    public int getAmountOfProduct() {
        return amountOfProduct;
    }
}