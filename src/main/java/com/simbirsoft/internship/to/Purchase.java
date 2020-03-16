package com.simbirsoft.internship.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Purchase {
    @JsonProperty("store_id")
    private Integer storeId;

    @JsonProperty("products_for_purchase")
    private Set<Position> positions = new HashSet<>();

    public Purchase() {
    }

    public Purchase(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
