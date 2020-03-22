package com.simbirsoft.internship;

import com.simbirsoft.internship.entity.*;
import com.simbirsoft.internship.dto.Position;
import com.simbirsoft.internship.dto.Purchase;
import com.simbirsoft.internship.dto.product.Product;
import com.simbirsoft.internship.dto.product.ProductWithId;

import java.util.*;

public class TestData {

    // Categories

    public static final CategoryEntity CAT_1 = new CategoryEntity(1, "Ёлки");
    public static final CategoryEntity CAT_2 = new CategoryEntity(2, "Ёлочные шары");
    public static final CategoryEntity CAT_3 = new CategoryEntity(3, "Гирлянды");
    public static final List<CategoryEntity> CATEGORY_ENTITIES = new ArrayList<>(Arrays.asList(CAT_1, CAT_2, CAT_3));

    public static final CategoryEntity CAT_FOR_UPDATE = new CategoryEntity(1, "Обновленная категория");
    public static final CategoryEntity DUPLICATE_NAME_CAT = new CategoryEntity(4, "Ёлочные шары");
    public static final CategoryEntity EMPTY_CAT_NAME = new CategoryEntity(5, "");
    public static final CategoryEntity INVALID_CAT_NAME = new CategoryEntity(6, "~Cat~%^NAME!");
    public static final CategoryEntity ONLY_SPACE_CAT_NAME = new CategoryEntity(7, "        ");

    // Products

    public static final ProductEntity PROD_ENTITY_1 = new ProductEntity(1, "Искусственная Ель 100см.", "Описание 1", 1000, 10, CAT_1);
    public static final ProductEntity PROD_ENTITY_2 = new ProductEntity(2, "Искусственная Ель 150см.", "Описание 2", 1500, 20, CAT_1);
    public static final ProductEntity PROD_ENTITY_3 = new ProductEntity(3, "Искусственная Ель 220см.", "Описание 3", 2200, 30, CAT_1);

    public static final Product PROD_DTO_1 = new Product("Искусственная Ель 100см.", "Описание 1", 1000, 10, CAT_1.getId());
    public static final Product PROD_DTO_2 = new Product("Искусственная Ель 150см.", "Описание 2", 1500, 20, CAT_1.getId());
    public static final Product PROD_DTO_3 = new Product("Искусственная Ель 220см.", "Описание 3", 2200, 30, CAT_1.getId());

    public static final Product PROD_DTO_INVALID_NAME = new Product("     ", "Описание 3", 2200, 1, CAT_1.getId());
    public static final Product PROD_DTO_INVALID_AMOUNT = new Product("Искусственная Ель 220см.", "Описание 3", 2200, -99, CAT_1.getId());
    public static final Product PROD_DTO_INVALID_PRICE = new Product("Искусственная Ель 220см.", "Описание 3", -99, 1, CAT_1.getId());
    public static final Product PROD_DTO_INVALID_CATID = new Product("Искусственная Ель 220см.", "Описание 3", 2200, 1, -99);

    public static final Product PROD_DTO_INVALID_DESC = new Product("Искусственная Ель 220см.", "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. ", 2200, 1, CAT_1.getId());

    // Purchase $ Positions

    public static final Position POSITION_1 = new Position(1, 1);
    public static final Position POSITION_2 = new Position(2, 2);
    public static final Position POSITION_3 = new Position(3, 3);

    // Correct Purchase
    public static final StoreEntity STORE = new StoreEntity(1, "Магазин на Гаражной", "г. Москва, ул. Гаражная, 1", new HashSet<>(Collections.singletonList(new PurchaseEntity())));
    public static final ProductEntity AVAILABLE_PROD = new ProductEntity(1, "Искусственная Ель 100см.", "Описание 1", 1000, 30, CAT_1);
    public static final ProductWithId PURCHASED_PRODS = new ProductWithId(1, "Искусственная Ель 100см.", "Описание 1", 1000, 1, CAT_1.getId());
    public static final Purchase CORRECT_PURCHASE = new Purchase(1, new HashSet<>(Collections.singletonList(POSITION_1)));

    // Lower than available products amount
    public static final Position POSITION_LOWER_THAN_AVAL = new Position(1, 100);
    public static final Purchase PURCHASE_LOWER_THAN_AVAL = new Purchase(1, new HashSet<>(Collections.singletonList(POSITION_LOWER_THAN_AVAL)));

    // Correct Purchase, but after that products are end
    public static final ProductEntity AVAILABLE_PROD_THAN_END = new ProductEntity(1, "Искусственная Ель 100см.", "Описание 1", 1000, 30, CAT_1);
    public static final Position POSITION_PRODUCT_END = new Position(1, 30);
    public static final Purchase PURCHASE_PRODUCT_END = new Purchase(1, new HashSet<>(Collections.singletonList(POSITION_PRODUCT_END)));

    // Purchase with Exceptions
    public static final Position POSITION_INVALID_AMOUNT = new Position(1, -10);
    public static final Position POSITION_INVALID_ID = new Position(-1, 10);
    public static final Purchase PURCHASE = new Purchase(1, new HashSet<>(Arrays.asList(POSITION_1, POSITION_2, POSITION_3)));
    public static final Purchase PURCHASE_INVALID_STOREID = new Purchase(-2, new HashSet<>(Arrays.asList(POSITION_1, POSITION_2, POSITION_3)));
    public static final Purchase PURCHASE_INVALID_AMOUNT = new Purchase(1, new HashSet<>(Arrays.asList(POSITION_INVALID_AMOUNT, POSITION_2, POSITION_3)));
    public static final Purchase PURCHASE_INVALID_ID = new Purchase(1, new HashSet<>(Arrays.asList(POSITION_INVALID_ID, POSITION_2, POSITION_3)));

    // Write-Off

    public static final WriteOffProductEntity WR_OFF_PROD_ENT_1 = new WriteOffProductEntity(1, "Продукт 1", "Описание 1", 1, 1, 300, 10, new WriteOffEntity());
    public static final WriteOffProductEntity WR_OFF_PROD_ENT_2 = new WriteOffProductEntity(2, "Продукт 2", "Описание 2", 2, 1, 300, 10, new WriteOffEntity());
    public static final WriteOffProductEntity WR_OFF_PROD_ENT_3 = new WriteOffProductEntity(3, "Продукт 3", "Описание 3", 3, 1, 300, 10, new WriteOffEntity());

    public static final WriteOffProductEntity WR_OFF_PROD_ENT_4 = new WriteOffProductEntity(4, "Продукт 4", "Описание 4", 4, 1, 400, 10, new WriteOffEntity());
    public static final WriteOffProductEntity WR_OFF_PROD_ENT_5 = new WriteOffProductEntity(5, "Продукт 5", "Описание 5", 5, 1, 500, 20, new WriteOffEntity());

    public static final WriteOffEntity WRITEOFF_FALSE = new WriteOffEntity(1, new HashSet<>(Arrays.asList(WR_OFF_PROD_ENT_1, WR_OFF_PROD_ENT_2, WR_OFF_PROD_ENT_3)), false);
    public static final WriteOffEntity WRITEOFF_TRUE = new WriteOffEntity(2, new HashSet<>(Arrays.asList(WR_OFF_PROD_ENT_4, WR_OFF_PROD_ENT_5)), true);
    public static final List<WriteOffEntity> WRITEOFF_ENTITIES = new ArrayList<>(Arrays.asList(WRITEOFF_FALSE, WRITEOFF_TRUE));
}