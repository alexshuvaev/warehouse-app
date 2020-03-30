package com.simbirsoft.internship.service;

import com.simbirsoft.internship.dto.report.RevenueReport;
import com.simbirsoft.internship.dto.report.StoreSalesReport;
import com.simbirsoft.internship.dto.report.WriteOffReport;

import java.time.LocalDate;

public interface ReportService {
    /**
     * Generate main KPI report.
     * - amount of purchases in all stores.
     * - total revenue for all stores.
     * - average check for all stores.
     * - each store revenue.
     * - each store average check.
     * - amount of purchases in each store.
     *
     * @param startDate generate report from startDate.
     * @param endDate generate report up to endDate.
     * @return RevenueReport entity.
     */
    RevenueReport getRevenueReport(LocalDate startDate, LocalDate endDate);

    /**
     * Generate sales report for single store.
     * - amount of sold products.
     * - total revenue.
     * - list of products in each category, which has been sold in the store.
     * - each category revenue.
     *
     * @param storeId id of the store.
     * @param startDate generate report from startDate.
     * @param endDate generate report up to endDate.
     * @return StoreSalesReport entity.
     */
    StoreSalesReport getStoreSalesReport(int storeId, LocalDate startDate, LocalDate endDate);

    /**
     * Generate WriteOff report.
     * - total sum of WriteOffs.
     * - list of products in each category, which has been write-off.
     *
     * @param startDate generate report from startDate.
     * @param endDate generate report up to endDate.
     * @return WriteOffReport entity.
     */
    WriteOffReport getWriteOffReport(LocalDate startDate, LocalDate endDate);
}
