package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.entity.PurchaseEntity;
import com.simbirsoft.internship.entity.StoreEntity;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.repository.PurchaseRepository;
import com.simbirsoft.internship.repository.StoreRepository;
import com.simbirsoft.internship.to.Purchase;
import com.simbirsoft.internship.to.product.ProductWithId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.simbirsoft.internship.util.TosConverter.*;
import static com.simbirsoft.internship.util.ValidationUtil.ProductsWhichAmountLowerThanAvailable;
import static com.simbirsoft.internship.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class PurchaseServiceImpl implements PurchaseService {
    PurchaseRepository purchaseRepository;
    ProductRepository productRepository;
    StoreRepository storeRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductRepository productRepository, StoreRepository storeRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional
    @Override
    public List<ProductWithId> makeAnPurchase(Purchase purchase) {
        // get StoreEntity where is purchase made
        StoreEntity storeEntity = storeRepository.getOne(purchase.getStoreId());
        // total bill of the PurchaseEntity
        AtomicReference<Double> totalBill = new AtomicReference<>((double) 0);
        // Products in Purchase
        List<ProductWithId> productsInPurchase = new ArrayList<>();
        // Products which available for purchase, and will not end after purchase
        List<ProductEntity> purchasedProductEntities = new ArrayList<>();
        // Products which available for purchase, and will end after this purchase
        List<ProductEntity> endedProductEntities = new ArrayList<>();
        // Products which not available for purchase in this quantity
        Map<ProductEntity, Integer> amountLowerThanAvailable = new HashMap<>();

        purchase.getPositions()
                .forEach(position -> {
                    // find productEntity by id, or null if not exist
                    Integer productid = position.getIdOfProduct();
                    ProductEntity productEntity = checkNotFoundWithId(productRepository.findById(productid).orElse(null), productid);
                    if (productEntity != null) {
                        int amountOfProduct = productEntity.getAmount();
                        int amountOfPurchasingProduct = position.getAmountOfProduct();
                        int available = amountOfProduct - amountOfPurchasingProduct;
                        // update availability of ProductEntity
                        if (available >= 0) {
                            ProductWithId productWithId = productWithIdCreate(productEntity);
                            productWithId.setAmount(amountOfPurchasingProduct);
                            productsInPurchase.add(productWithId);

                            productEntity.setAmount(available);
                            purchasedProductEntities.add(productEntity);
                            totalBill.updateAndGet(v -> v + (productEntity.getPrice() * amountOfPurchasingProduct));
                        }
                        if (available == 0) {
                            endedProductEntities.add(productEntity);
                        }
                        if (available < 0) {
                            amountLowerThanAvailable.put(productEntity, amountOfPurchasingProduct);
                            ProductsWhichAmountLowerThanAvailable(amountLowerThanAvailable);
                        }
                    }
                });

        if (!purchasedProductEntities.isEmpty()) {
            // saving data about purchase in DB
            purchaseRepository.save(new PurchaseEntity(null, storeEntity, new HashSet<>(purchasedProductEntities), totalBill.get()));
            // updating Products quantities after purchasing
            productRepository.saveAll(purchasedProductEntities);
        }
        if (!endedProductEntities.isEmpty()) {
            // deleting Products which ended
            for (ProductEntity productEntity : endedProductEntities) {
                productRepository.delete(productEntity);
            }
        }
        return productsInPurchase;
    }
}
