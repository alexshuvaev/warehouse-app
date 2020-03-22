package com.simbirsoft.internship.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryWithId extends Category {
    @JsonProperty("category_id")
    Integer id;

    @JsonProperty("amount_of_products")
    Integer amountOfProducts;

    public CategoryWithId() {
    }

    public CategoryWithId(Integer id, String name) {
        super(name);
        this.id = id;
    }

    public CategoryWithId(Integer id, String name, Integer amountOfProducts) {
        this(id, name);
        this.amountOfProducts = amountOfProducts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
