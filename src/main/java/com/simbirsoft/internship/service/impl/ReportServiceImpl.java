package com.simbirsoft.internship.service.impl;

import com.simbirsoft.internship.dto.report.ProductForReport;
import com.simbirsoft.internship.dto.report.RevenueReport;
import com.simbirsoft.internship.dto.report.StoreSalesReport;
import com.simbirsoft.internship.dto.report.WriteOffReport;
import com.simbirsoft.internship.entity.PurchaseEntity;
import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.entity.WriteOffProductEntity;
import com.simbirsoft.internship.repository.PurchaseRepository;
import com.simbirsoft.internship.repository.WriteOffRepository;
import com.simbirsoft.internship.service.ReportService;
import com.simbirsoft.internship.util.exception.InvalidPropertyException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

@Service
public class ReportServiceImpl implements ReportService {
    private PurchaseRepository purchaseRepository;
    private WriteOffRepository writeOffRepository;

    @Autowired
    public ReportServiceImpl(PurchaseRepository purchaseRepository, WriteOffRepository writeOffRepository) {
        this.purchaseRepository = purchaseRepository;
        this.writeOffRepository = writeOffRepository;
    }

    @Override
    public RevenueReport getRevenueReport(LocalDate startDate, LocalDate endDate) {

        // ** Prepare data. ** //

        LocalDateTime[] between = convertToLocalDateTime(startDate, endDate);

        // Get all Purchase entities.
        List<PurchaseEntity> purchaseEntities = purchaseRepository.getAllBetween(between[0], between[1]);
        isDataExist(purchaseEntities);
        // Integer - store id,
        // List<Double> - list of purchase total price in each store.
        Map<Integer, List<Double>> storesRevenue = purchaseEntities.stream()
                .collect(Collectors.groupingBy(e -> e.getStore().getId(),
                        Collectors.mapping(
                                PurchaseEntity::getTotalPrice, Collectors.toList())));

        // ** Get necessary data. ** //

        // Integer - store id,
        // Double - sum of purchases total price.
        Map<Integer, Double> eachStoreRevenueSum =
                storesRevenue.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream().mapToDouble(Double::doubleValue).sum()
                        ));

        // Integer - store id,
        // Double - average check of all purchases.
        Map<Integer, Double> eachStoreAvgCheck =
                storesRevenue.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream()
                                        .mapToDouble(p -> p).average().orElse(0.0)
                        ));

        // Integer - store id,
        // Integer - counting purchases.
        Map<Integer, Integer> eachStorePurchaseCount =
                storesRevenue.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().size()
                        ));

        // Total purchases - all stores.
        double totalPurchaseCount = purchaseEntities.size();

        // Total revenue - all stores.
        double totalRevenue = eachStoreRevenueSum.values().stream()
                .mapToDouble(e -> e).sum();

        // Average check - all stores.
        double totalAvgCheck = totalRevenue / totalPurchaseCount;

        return new RevenueReport(
                totalPurchaseCount,
                totalRevenue,
                totalAvgCheck,
                eachStoreRevenueSum,
                eachStoreAvgCheck,
                eachStorePurchaseCount);
    }

    @Override
    public StoreSalesReport getStoreSalesReport(int storeId, LocalDate startDate, LocalDate endDate) {

        // ** Prepare data. ** //

        LocalDateTime[] between = convertToLocalDateTime(startDate, endDate);
        // Get data from different columns of several tables.
        List<Tuple> soldPurchases = purchaseRepository.getSoldPurchases(storeId, between[0], between[1]);
        isDataExist(soldPurchases);
        // ** Get necessary data. ** //

        // Integer - category id,
        // List<ProductForReport> - list of products in category.
        Map<Integer, List<ProductForReport>> soldProductsInEachCategory = new HashMap<>();
        for (Tuple elem : soldPurchases) {
            String name = (String) elem.get("name");
            double priceForUnit = (double) elem.get("price");
            int amount = Integer.parseInt(valueOf(elem.get("amount")));
            double totalPrice = priceForUnit * amount;
            int categoryId = (int) elem.get("cat");

            fillMap(soldProductsInEachCategory, name, priceForUnit, amount, totalPrice, categoryId);
        }

        // Integer - category id,
        // Integer - category revenue.
        Map<Integer, Double> eachCategoryRevenue =
                soldProductsInEachCategory.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream()
                                        .mapToDouble(p -> p.getPriceForUnit() * p.getAmount()).sum()));

        // Amount of sold products - all categories.
        int totalUnitSold = soldPurchases.stream()
                .mapToInt(e ->
                        Integer.parseInt(valueOf(e.get("amount")))
                ).sum();

        // Store revenue - all categories.
        double totalRevenue = soldPurchases.stream()
                .mapToDouble(e ->
                        Double.parseDouble(valueOf(e.get("price"))) * Integer.parseInt(valueOf(e.get("amount")))
                ).sum();

        return new StoreSalesReport(
                storeId,
                totalUnitSold,
                totalRevenue,
                soldProductsInEachCategory,
                eachCategoryRevenue
        );
    }

    @Override
    public WriteOffReport getWriteOffReport(LocalDate startDate, LocalDate endDate) {

        // ** Prepare data. ** //

        LocalDateTime[] between = convertToLocalDateTime(startDate, endDate);
        List<WriteOffEntity> writeOffEntities = writeOffRepository.getAllBetween(between[0], between[1]);
        isDataExist(writeOffEntities);
        // ** Get necessary data. ** //

        // Total sum - all writeoffs.
        double totalSumOfWriteOffs = writeOffEntities.stream()
                .mapToDouble(WriteOffEntity::getTotalPrice).sum();

        // Integer - category id.
        // List<ProductForReport> - products in category
        Map<Integer, List<ProductForReport>> writeoffProductsInEachCategory = new HashMap<>();
        for (WriteOffEntity writeOffEntity : writeOffEntities) {
            for (WriteOffProductEntity p : writeOffEntity.getProducts()) {
                String name = p.getName();
                double priceForUnit = p.getPrice();
                int amount = p.getAmount();
                double totalPrice = priceForUnit * amount;
                int categoryId = p.getCategoryId();

                fillMap(writeoffProductsInEachCategory, name, priceForUnit, amount, totalPrice, categoryId);
            }
        }
        return new WriteOffReport(totalSumOfWriteOffs, writeoffProductsInEachCategory);
    }

    private void fillMap(Map<Integer, List<ProductForReport>> map, String name, double priceForUnit, int amount, double totalPrice, int categoryId) {
        ProductForReport product = new ProductForReport(name, priceForUnit, amount, totalPrice);
        if (map.containsKey(categoryId)) {
            map.get(categoryId).add(product);
        } else {
            List<ProductForReport> products = new ArrayList<>();
            products.add(product);
            map.put(categoryId, products);
        }
    }

    private <T> void isDataExist(List<T> list){
        if (list.isEmpty()) throw new NotFoundException("Report cannot be generated. Data not found.");
    }

    private LocalDateTime[] convertToLocalDateTime(LocalDate startDate, LocalDate endDate) {
        LocalDateTime[] between = new LocalDateTime[2];
        if (startDate == null && endDate == null) {
            between[0] = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
            between[1] = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        } else if (startDate == null) {
            between[0] = LocalDateTime.of(LocalDate.of(2020,1,1), LocalTime.of(0, 0, 0));
            between[1] = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));
        } else if (endDate == null) {
            between[0] = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
            between[1] = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        } else {
            between[0] = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
            between[1] = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));
        }
        if(between[0].isAfter(between[1])) throw new InvalidPropertyException("startDate can't be after endDate.");
        return between;
    }
}
