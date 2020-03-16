package com.simbirsoft.internship.util;

import com.simbirsoft.internship.entity.AbstractBaseEntity;
import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.util.exception.LowerThanAvaibleException;
import com.simbirsoft.internship.util.exception.MustBeUniqueException;
import com.simbirsoft.internship.util.exception.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static List<ProductEntity> checkNotFoundWithList(List<ProductEntity> list, Set<Integer> ids) {
        Set<Integer> foundedIds = new HashSet<>();
        if (list.size() < ids.size()){
            for (AbstractBaseEntity entity : list) {
                int id = entity.getId();
                foundedIds.add(id);
            }
            ids.removeAll(foundedIds);
            throw new NotFoundException("Not found entities with ids=" + ids.toString(), null, true, false);
        }
        return list;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg, null, true, false);
        }
    }

    public static <T> T checkUniqueName(boolean existsByName, T object) {
        if (existsByName) {
            throw new MustBeUniqueException("Entity with name: " + object + " already exist (name must be unique)", null, true, false);
        }
        return object;
    }

    public static void ProductsWhichAmountLowerThanAvailable(Map<ProductEntity, Integer> amountLowerThanAvailable) {
        StringBuilder sb = new StringBuilder();
        amountLowerThanAvailable.forEach((key, value) ->
                sb
                .append("id=")
                .append(key.getId())
                .append(", name=")
                .append(key.getName())
                .append(", in purchase=")
                .append(value)
                .append(", but available=")
                .append(key.getAmount())
                .append("     "));

        throw new LowerThanAvaibleException(sb.toString() + "Products can't be purchased. The quantity of products is lower than available. Change amount of ProductEntity or delete it from your request", null, true, false);
    }
}