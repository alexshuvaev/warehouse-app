package com.simbirsoft.internship.testdata;

import com.simbirsoft.internship.dto.report.ProductForReport;
import com.simbirsoft.internship.dto.report.RevenueReport;
import com.simbirsoft.internship.dto.report.WriteOffReport;
import com.simbirsoft.internship.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class ReportTestData {
    public static final LocalDate START_DATE = LocalDate.of(2020, 3, 20);
    public static final LocalDate END_DATE = LocalDate.of(2020, 3, 30);
    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(START_DATE, LocalTime.of(0, 0, 0));
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(END_DATE, LocalTime.of(23, 59, 59));

    public static final StoreEntity STORE = new StoreEntity(1, "Магазин на Гаражной", "г. Москва, ул. Гаражная, 1", new HashSet<>(Collections.singletonList(new PurchaseEntity())));

    public static final PurchaseProductEntity PURCHASE_PRODUCT_ENTITY = new PurchaseProductEntity(1, "Искусственная Ель 100см.", "Описание 1", 1000, 1, 1, new PurchaseEntity());
    public static final PurchaseEntity PURCHASE_ENTITY = new PurchaseEntity(1, STORE, new HashSet<>(Collections.singletonList(PURCHASE_PRODUCT_ENTITY)), 1000);

    public static final WriteOffProductEntity WR_OFF_PROD_ENT_1 = new WriteOffProductEntity(1, "Продукт 1", "Описание 1", 1, 1, 300, 10, new WriteOffEntity());
    public static final WriteOffProductEntity WR_OFF_PROD_ENT_2 = new WriteOffProductEntity(2, "Продукт 2", "Описание 2", 2, 1, 300, 10, new WriteOffEntity());
    public static final WriteOffEntity WRITEOFF_FALSE = new WriteOffEntity(1, new HashSet<>(Arrays.asList(WR_OFF_PROD_ENT_1, WR_OFF_PROD_ENT_2)), false, 600.0);
    public static final WriteOffEntity WRITEOFF_TRUE = new WriteOffEntity(2, new HashSet<>(Arrays.asList(WR_OFF_PROD_ENT_1, WR_OFF_PROD_ENT_2)), true, 600.0);
    public static final List<WriteOffEntity> WRITEOFF_ENTITIES = new ArrayList<>(Arrays.asList(WRITEOFF_FALSE, WRITEOFF_TRUE));

    public static final Map<Integer, Double> EACH_STORE_REVENUE_SUM = new HashMap<>();
    public static final Map<Integer, Double> EACH_STORE_AVG_CHECK = new HashMap<>();
    public static final Map<Integer, Integer> EACH_STORE_PURCHASE_COUNT = new HashMap<>();
    public static final Map<Integer, List<ProductForReport>> WRITEOFF_PROD_EACH_CAT = new HashMap<>();
    public static final ProductForReport PROD_FOR_REP_1 = new ProductForReport("Продукт 1", 300, 10, 3000.0);
    public static final ProductForReport PROD_FOR_REP_2 = new ProductForReport("Продукт 2", 300, 10, 3000.0);
    public static final List<ProductForReport> PROD_FOR_REPORT = new ArrayList<>(Arrays.asList(PROD_FOR_REP_2, PROD_FOR_REP_1, PROD_FOR_REP_2, PROD_FOR_REP_1));

    static {
        EACH_STORE_REVENUE_SUM.put(1, 1000.0);
        EACH_STORE_AVG_CHECK.put(1, 1000.0);
        EACH_STORE_PURCHASE_COUNT.put(1, 1);
        WRITEOFF_PROD_EACH_CAT.put(1, PROD_FOR_REPORT);
    }

    public static final RevenueReport REVENUE_REPORT = new RevenueReport(1, 1000, 1000, EACH_STORE_REVENUE_SUM, EACH_STORE_AVG_CHECK, EACH_STORE_PURCHASE_COUNT);
    public static final WriteOffReport WRITEOFF_REPORT = new WriteOffReport(1200.0, WRITEOFF_PROD_EACH_CAT);
}
