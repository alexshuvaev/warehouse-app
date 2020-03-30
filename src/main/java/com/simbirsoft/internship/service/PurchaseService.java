package com.simbirsoft.internship.service;

import com.simbirsoft.internship.dto.Purchase;
import com.simbirsoft.internship.entity.PurchaseEntity;

public interface PurchaseService {
   /**
    * Make an Purchase
    *
    * @param purchase with Positions of Products.
    *                 each Position contains id and quantity of Product.
    *                 Purchase also contains Store id.
    * @return List of Products which purchased.
    * If in Purchase amount of Product greater than available, will be LowerThanAvaibleException.
    * If at least one Product can't be found by id, will be NotFoundException.
    */
   PurchaseEntity makeAnPurchase(Purchase purchase);
}
