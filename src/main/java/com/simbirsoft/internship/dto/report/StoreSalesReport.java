package com.simbirsoft.internship.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class StoreSalesReport {
    @JsonProperty("storeId")
    private int storeId;
    @JsonProperty("total_unit_sold")
    private int totalUnitSold;
    @JsonProperty("total_revenue")
    private double totalRevenue;
    @JsonProperty("categoryId_and_sold_products")
    private Map<Integer, List<ProductForReport>> soldProductsInEachCategory;
    @JsonProperty("categoryId_and_revenue")
    private Map<Integer, Double> eachCategoryRevenue;

    public StoreSalesReport(int storeId, int totalUnitSold, double totalRevenue,
                            Map<Integer, List<ProductForReport>> soldProductsInEachCategory,
                            Map<Integer, Double> eachCategoryRevenue) {
        this.storeId = storeId;
        this.totalUnitSold = totalUnitSold;
        this.totalRevenue = totalRevenue;
        this.soldProductsInEachCategory = soldProductsInEachCategory;
        this.eachCategoryRevenue = eachCategoryRevenue;
    }
}
