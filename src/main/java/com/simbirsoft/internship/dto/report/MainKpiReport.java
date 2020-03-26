package com.simbirsoft.internship.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.stream.Collectors;

public class MainKpiReport {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    @JsonProperty("storeId_and_profit_sum")
    private Map<Integer, Double> eachStoreProfitSum;
    @JsonProperty("storeId_and_average_check")
    private Map<Integer, Double> eachStoreProfitAvg;
    @JsonProperty("storeId_and_count_of_purchases")
    private Map<Integer, Long> eachStorePurchaseCount;
    @JsonProperty("total_purchases_count")
    private double totalPurchaseCount;
    @JsonProperty("total_profit")
    private double totalProfit;
    @JsonProperty("average_check")
    private double averageCheck;

    public MainKpiReport(Map<Integer, Double> eachStoreProfitSum,
                         Map<Integer, Double> eachStoreProfitAvg,
                         Map<Integer, Long> eachStorePurchaseCount,
                         double totalPurchaseCount,
                         double totalProfit,
                         double averageCheck) {
        this.eachStoreProfitSum = eachStoreProfitSum;
        this.eachStoreProfitAvg = eachStoreProfitAvg.entrySet().stream()
                .collect(Collectors.toMap(
                        k -> k.getValue().intValue(),
                        v -> Double.parseDouble(df2.format(v.getValue()))));
        this.eachStorePurchaseCount = eachStorePurchaseCount;
        this.totalPurchaseCount = totalPurchaseCount;
        this.totalProfit = totalProfit;
        this.averageCheck = averageCheck;
    }

    public Map<Integer, Double> getEachStoreProfitSum() {
        return eachStoreProfitSum;
    }

    public void setEachStoreProfitSum(Map<Integer, Double> eachStoreProfitSum) {
        this.eachStoreProfitSum = eachStoreProfitSum;
    }

    public Map<Integer, Double> getEachStoreProfitAvg() {
        return eachStoreProfitAvg;
    }

    public void setEachStoreProfitAvg(Map<Integer, Double> eachStoreProfitAvg) {
        this.eachStoreProfitAvg = eachStoreProfitAvg;
    }

    public Map<Integer, Long> getEachStorePurchaseCount() {
        return eachStorePurchaseCount;
    }

    public void setEachStorePurchaseCount(Map<Integer, Long> eachStorePurchaseCount) {
        this.eachStorePurchaseCount = eachStorePurchaseCount;
    }

    public double getTotalPurchaseCount() {
        return totalPurchaseCount;
    }

    public void setTotalPurchaseCount(double totalPurchaseCount) {
        this.totalPurchaseCount = totalPurchaseCount;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getAverageCheck() {
        return averageCheck;
    }

    public void setAverageCheck(double averageCheck) {
        this.averageCheck = averageCheck;
    }
}
