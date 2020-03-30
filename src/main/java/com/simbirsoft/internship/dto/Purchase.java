package com.simbirsoft.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Purchase {
    @JsonProperty("store_id")
    private int storeId;

    @JsonProperty("products")
    private Set<Position> positions = new HashSet<>();

    public Purchase() {
    }

    public Purchase(Set<Position> positions) {
        this.positions = positions;
    }

    public Purchase(int storeId, Set<Position> positions) {
        this(positions);
        this.storeId = storeId;
    }

    public int getStoreId() {
        return storeId;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        return "storeId=" + storeId + " positions=" + positions;
    }
}
