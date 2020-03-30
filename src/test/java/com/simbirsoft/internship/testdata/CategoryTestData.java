package com.simbirsoft.internship.testdata;

import com.simbirsoft.internship.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryTestData {
    public static final CategoryEntity CAT_1 = new CategoryEntity(1, "Ёлки");
    public static final CategoryEntity CAT_2 = new CategoryEntity(2, "Ёлочные шары");
    public static final List<CategoryEntity> CATEGORY_ENTITIES = new ArrayList<>(Arrays.asList(CAT_1, CAT_2));

    public static final CategoryEntity CAT_FOR_UPDATE = new CategoryEntity(1, "Обновленная категория");
    public static final CategoryEntity DUPLICATE_NAME_CAT = new CategoryEntity(4, "Ёлочные шары");
    public static final CategoryEntity EMPTY_CAT_NAME = new CategoryEntity(5, "");
    public static final CategoryEntity INVALID_CAT_NAME = new CategoryEntity(6, "~Cat~%^NAME!");
    public static final CategoryEntity ONLY_SPACE_CAT_NAME = new CategoryEntity(7, "        ");
}
