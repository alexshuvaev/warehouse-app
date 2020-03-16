package com.simbirsoft.internship.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class WriteOff {
    @JsonProperty("products")
    private Set<Position> positionsForWriteoff = new HashSet<>();

    public WriteOff() {
    }

    public WriteOff(Set<Position> positionsForWriteoff) {
        this.positionsForWriteoff = positionsForWriteoff;
    }

    public Set<Position> getPositionsForWriteoff() {
        return positionsForWriteoff;
    }

    public void setPositionsForWriteoff(Set<Position> positionsForWriteoff) {
        this.positionsForWriteoff = positionsForWriteoff;
    }
}