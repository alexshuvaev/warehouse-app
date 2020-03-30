package com.simbirsoft.internship.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RevenueReport {
    @JsonProperty("total_purchases_count")
    private double totalPurchaseCount;
    @JsonProperty("total_revenue")
    private double totalRevenue;
    @JsonProperty("total_average_check")
    private double totalAvgCheck;
    @JsonProperty("storeId_and_revenue_sum")
    private Map<Integer, Double> eachStoreRevenueSum;
    @JsonProperty("storeId_and_average_check")
    private Map<Integer, Double> eachStoreAvgCheck;
    @JsonProperty("storeId_and_count_of_purchases")
    private Map<Integer, Integer> eachStorePurchaseCount;

    public RevenueReport(double totalPurchaseCount,
                         double totalRevenue,
                         double totalAvgCheck,
                         Map<Integer, Double> eachStoreRevenueSum,
                         Map<Integer, Double> eachStoreAvgCheck,
                         Map<Integer, Integer> eachStorePurchaseCount) {
        this.totalPurchaseCount = totalPurchaseCount;
        this.totalRevenue = totalRevenue;
        this.totalAvgCheck = totalAvgCheck;

        this.eachStoreRevenueSum = eachStoreRevenueSum;
        this.eachStoreAvgCheck = eachStoreAvgCheck.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
        this.eachStorePurchaseCount = eachStorePurchaseCount;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RevenueReport)) return false;
        RevenueReport that = (RevenueReport) o;
        return Double.compare(that.totalPurchaseCount, totalPurchaseCount) == 0 &&
                Double.compare(that.totalRevenue, totalRevenue) == 0 &&
                Double.compare(that.totalAvgCheck, totalAvgCheck) == 0 &&
                Objects.equals(eachStoreRevenueSum, that.eachStoreRevenueSum) &&
                Objects.equals(eachStoreAvgCheck, that.eachStoreAvgCheck) &&
                Objects.equals(eachStorePurchaseCount, that.eachStorePurchaseCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPurchaseCount, totalRevenue, totalAvgCheck, eachStoreRevenueSum, eachStoreAvgCheck, eachStorePurchaseCount);
    }

    @Override
    public String toString() {
        return "totalPurchaseCount=" + totalPurchaseCount + " totalRevenue=" + totalRevenue + " totalAvgCheck=" + totalAvgCheck +
                " eachStoreRevenueSum=" + eachStoreRevenueSum + " eachStoreAvgCheck=" + eachStoreAvgCheck + " eachStorePurchaseCount=" + eachStorePurchaseCount;
    }
}
