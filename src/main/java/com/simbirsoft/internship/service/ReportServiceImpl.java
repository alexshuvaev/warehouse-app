package com.simbirsoft.internship.service;

import com.simbirsoft.internship.dto.report.MainKpiReport;
import com.simbirsoft.internship.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private PurchaseRepository purchaseRepository;

    @Autowired
    public ReportServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public MainKpiReport getMainKpi(LocalDate startDate, LocalDate endDate) {
        List<Tuple> projections = purchaseRepository.getMainKpi(
                LocalDateTime.of(startDate, LocalTime.of(0, 0, 0)),
                LocalDateTime.of(endDate, LocalTime.of(23, 59, 59)));

        // Integer - storeId,
        // List<Double> - list of purchases total_price.
        Map<Integer, List<Double>> storesProfit = projections.stream()
                .collect(Collectors.groupingBy(e -> (int) e.get(0),
                        Collectors.mapping(
                                p -> (double) p.get(1), Collectors.toList())));

        // Integer - storeId,
        // Double - sum of purchases total_price.
        Map<Integer, Double> eachStoreProfitSum =
                storesProfit.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream().mapToDouble(Double::doubleValue).sum()
                        ));

        // Integer - storeId,
        // Double - avg of purchases total_price.
        Map<Integer, Double> eachStoreProfitAvg =
                storesProfit.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream().mapToDouble(a -> a).average().orElse(0.0)
                        ));

        // Integer - storeId,
        // Long - counting purchases.
        Map<Integer, Long> eachStorePurchaseCount =
                storesProfit.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> (long) e.getValue().size()
                        ));

        // Total purchases - all stores.
        double totalPurchaseCount = projections.size();

        // Total Profit - all stores.
        double totalProfit = eachStoreProfitSum.values().stream()
                .mapToDouble(e -> e).sum();

        // Average Check - all stores.
        double averageCheck = totalProfit / totalPurchaseCount;

        return new MainKpiReport(
                eachStoreProfitSum,
                eachStoreProfitAvg,
                eachStorePurchaseCount,
                totalPurchaseCount,
                totalProfit,
                averageCheck);
    }
}
