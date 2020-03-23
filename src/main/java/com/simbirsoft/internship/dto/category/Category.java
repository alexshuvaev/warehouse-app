package com.simbirsoft.internship.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
    @JsonProperty("category_name")
    String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return '(' + name + ')';
    }
}