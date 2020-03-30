package com.simbirsoft.internship.testdata;

import com.simbirsoft.internship.dto.Position;
import com.simbirsoft.internship.dto.WriteOff;
import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.entity.WriteOffEntity;
import com.simbirsoft.internship.entity.WriteOffProductEntity;

import java.util.*;

public class WriteOffTestData {
    public static final CategoryEntity CAT_1 = new CategoryEntity(1, "Ёлки");
    public static final Position POSITION_1 = new Position(1, 1);
    public static final Position POSITION_2 = new Position(2, 2);

    public static final Position POSITION_LOWER_THAN_AVAL = new Position(1, 100);
    public static final Position POSITION_INVALID_AMOUNT = new Position(1, -10);
    public static final Position POSITION_INVALID_ID = new Position(-1, 10);

    public static final ProductEntity PROD_ENTITY_1 = new ProductEntity(1, "Искусственная Ель 100см.", "Описание 1", 1000, 10, CAT_1);
    public static final ProductEntity PROD_ENTITY_2 = new ProductEntity(2, "Искусственная Ель 150см.", "Описание 2", 1500, 20, CAT_1);

    public static final WriteOff WRITEOFF = new WriteOff(new HashSet<>(Arrays.asList(POSITION_1, POSITION_2)));
    public static final WriteOff WRITEOFF_LOWER_THAN_AVAL = new WriteOff(new HashSet<>(Collections.singletonList(POSITION_LOWER_THAN_AVAL)));
    public static final WriteOff WRITEOFF_INVALID_PRODUCTID = new WriteOff(new HashSet<>(Collections.singletonList(POSITION_INVALID_ID)));
    public static final WriteOff WRITEOFF_INVALID_AMOUNT = new WriteOff(new HashSet<>(Collections.singletonList(POSITION_INVALID_AMOUNT)));

    public static final WriteOffProductEntity WR_OFF_PROD_ENT_1 = new WriteOffProductEntity(1, "Продукт 1", "Описание 1", 1, 1, 300, 10, new WriteOffEntity());
    public static final WriteOffProductEntity WR_OFF_PROD_ENT_2 = new WriteOffProductEntity(2, "Продукт 2", "Описание 2", 2, 1, 300, 10, new WriteOffEntity());

    public static final WriteOffEntity WRITEOFF_FALSE = new WriteOffEntity(1, new HashSet<>(Arrays.asList(WR_OFF_PROD_ENT_1, WR_OFF_PROD_ENT_2)), false);
    public static final WriteOffEntity WRITEOFF_TRUE = new WriteOffEntity(2, new HashSet<>(Arrays.asList(WR_OFF_PROD_ENT_1, WR_OFF_PROD_ENT_2)), true);
    public static final List<WriteOffEntity> WRITEOFF_ENTITIES = new ArrayList<>(Arrays.asList(WRITEOFF_FALSE, WRITEOFF_TRUE));
}
