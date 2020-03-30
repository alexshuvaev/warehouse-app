package com.simbirsoft.internship.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryId extends Category {
    @JsonProperty("category_id")
    Integer id;

    @JsonProperty("amount_of_products")
    Integer amountOfProducts;

    public CategoryId() {
    }

    public CategoryId(String name, Integer id, Integer amountOfProducts) {
        super(name);
        this.id = id;
        this.amountOfProducts = amountOfProducts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString() + " id=" + id + "amountOfProducts=" + amountOfProducts;
    }
}
