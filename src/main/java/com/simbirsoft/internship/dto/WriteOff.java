package com.simbirsoft.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class WriteOff {
    @JsonProperty("products")
    private Set<Position> positions = new HashSet<>();

    public WriteOff() {
    }

    public WriteOff(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        return "positions=" + positions;
    }
}
