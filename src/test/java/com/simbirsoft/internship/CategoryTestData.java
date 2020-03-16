package com.simbirsoft.internship;

import com.simbirsoft.internship.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryTestData {
    public static final int START_SEQ = 1;
    public static final CategoryEntity CAT_1 = new CategoryEntity(START_SEQ, "Ёлки");
    public static final CategoryEntity CAT_2 = new CategoryEntity(START_SEQ+1, "Елочные шары");
    public static final CategoryEntity CAT_3 = new CategoryEntity(START_SEQ+2, "Гирлянды");
    public static final List<CategoryEntity> CATEGORY_ENTITIES = new ArrayList<>(Arrays.asList(CAT_1, CAT_2, CAT_3));

    public static final CategoryEntity NEW_CAT = new CategoryEntity(START_SEQ+3, "Новая категория");
}
