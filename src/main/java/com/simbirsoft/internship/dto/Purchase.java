package com.simbirsoft.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Purchase {
    @JsonProperty("store_id")
    private int storeId;

    @JsonProperty("products_for_purchase")
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

    public Set<Position> getPositions() {
        return positions;
    }

    public int getStoreId() {
        return storeId;
    }
}
