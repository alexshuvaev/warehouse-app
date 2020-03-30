package com.simbirsoft.internship.testdata;

import com.simbirsoft.internship.dto.product.ProductDesc;
import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.entity.ProductEntity;

public class ProductTestData {
    public static final CategoryEntity CAT_1 = new CategoryEntity(1, "Ёлки");

    public static final ProductEntity PROD_ENTITY_1 = new ProductEntity(1, "Искусственная Ель 100см.", "Описание 1", 1000, 10, CAT_1);
    public static final ProductEntity PROD_ENTITY_2 = new ProductEntity(2, "Искусственная Ель 150см.", "Описание 2", 1500, 20, CAT_1);

    public static final ProductDesc PROD_DTO_1 = new ProductDesc("Искусственная Ель 100см.", "Описание 1", 1000, 10, CAT_1.getId());
    public static final ProductDesc PROD_DTO_2 = new ProductDesc("Искусственная Ель 150см.", "Описание 2", 1500, 20, CAT_1.getId());

    public static final ProductDesc PROD_DTO_INVALID_NAME = new ProductDesc("     ", "Описание 3", 2200, 1, CAT_1.getId());
    public static final ProductDesc PROD_DTO_INVALID_AMOUNT = new ProductDesc("Искусственная Ель 220см.", "Описание 3", 2200, -99, CAT_1.getId());
    public static final ProductDesc PROD_DTO_INVALID_PRICE = new ProductDesc("Искусственная Ель 220см.", "Описание 3", -99, 1, CAT_1.getId());
    public static final ProductDesc PROD_DTO_INVALID_CATID = new ProductDesc("Искусственная Ель 220см.", "Описание 3", 2200, 1, -99);
    public static final ProductDesc PROD_DTO_INVALID_DESC = new ProductDesc("Искусственная Ель 220см.", "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. Текст более 500 символов. " +
            "Текст более 500 символов. ", 2200, 1, CAT_1.getId());
}
