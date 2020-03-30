package com.simbirsoft.internship.testdata;

import com.simbirsoft.internship.dto.Position;
import com.simbirsoft.internship.dto.Purchase;
import com.simbirsoft.internship.entity.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class PurchaseTestData {
    public static final CategoryEntity CAT_1 = new CategoryEntity(1, "Ёлки");
    public static final StoreEntity STORE = new StoreEntity(1, "Магазин на Гаражной", "г. Москва, ул. Гаражная, 1", new HashSet<>(Collections.singletonList(new PurchaseEntity())));

    public static final Position POSITION_1 = new Position(1, 1);
    public static final Position POSITION_2 = new Position(2, 2);
    public static final Position POSITION_INVALID_AMOUNT = new Position(1, -10);
    public static final Position POSITION_INVALID_ID = new Position(-1, 10);
    public static final Position POSITION_LOWER_THAN_AVAL = new Position(1, 100);

    public static final Purchase CORRECT_PURCHASE = new Purchase(1, new HashSet<>(Collections.singletonList(POSITION_1)));
    public static final Purchase PURCHASE_LOWER_THAN_AVAL = new Purchase(1, new HashSet<>(Collections.singletonList(POSITION_LOWER_THAN_AVAL)));
    public static final Purchase PURCHASE_INVALID_STOREID = new Purchase(-2, new HashSet<>(Arrays.asList(POSITION_1, POSITION_2)));
    public static final Purchase PURCHASE_INVALID_AMOUNT = new Purchase(1, new HashSet<>(Arrays.asList(POSITION_INVALID_AMOUNT, POSITION_2)));
    public static final Purchase PURCHASE_INVALID_ID = new Purchase(1, new HashSet<>(Arrays.asList(POSITION_INVALID_ID, POSITION_2)));

    public static final PurchaseProductEntity PURCHASE_PRODUCT_ENTITY = new PurchaseProductEntity(null, "Искусственная Ель 100см.", "Описание 1", 1000, 1, 1, new PurchaseEntity());
    public static final PurchaseEntity PURCHASE_ENTITY = new PurchaseEntity(null, STORE, new HashSet<>(Collections.singletonList(PURCHASE_PRODUCT_ENTITY)), 1000);
    public static final ProductEntity AVAILABLE_PROD = new ProductEntity(1, "Искусственная Ель 100см.", "Описание 1", 1000, 30, CAT_1);
}
