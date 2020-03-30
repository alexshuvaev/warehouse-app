package com.simbirsoft.internship.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WriteOffReport {
    @JsonProperty("total_sum_of_writeoffs")
    private double totalSumOfWriteOffs;

    @JsonProperty("categoryId_and_writeoff_products")
    private Map<Integer, List<ProductForReport>> writeoffProductsInEachCategory;

    public WriteOffReport(double totalSumOfWriteOff, Map<Integer, List<ProductForReport>> writeoffProductsInEachCategory) {
        this.totalSumOfWriteOffs = totalSumOfWriteOff;
        this.writeoffProductsInEachCategory = writeoffProductsInEachCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WriteOffReport)) return false;
        WriteOffReport that = (WriteOffReport) o;
        return Double.compare(that.totalSumOfWriteOffs, totalSumOfWriteOffs) == 0 &&
                Objects.equals(writeoffProductsInEachCategory, that.writeoffProductsInEachCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalSumOfWriteOffs, writeoffProductsInEachCategory);
    }

    @Override
    public String toString() {
        return "totalSumOfWriteOffs " + totalSumOfWriteOffs + " writeoffProductsInEachCategory " + writeoffProductsInEachCategory;
    }
}
