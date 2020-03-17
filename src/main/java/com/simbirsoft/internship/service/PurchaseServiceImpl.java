package com.simbirsoft.internship.service;

import com.simbirsoft.internship.entity.ProductEntity;
import com.simbirsoft.internship.entity.PurchaseEntity;
import com.simbirsoft.internship.entity.StoreEntity;
import com.simbirsoft.internship.repository.ProductRepository;
import com.simbirsoft.internship.repository.PurchaseRepository;
import com.simbirsoft.internship.repository.StoreRepository;
import com.simbirsoft.internship.to.Purchase;
import com.simbirsoft.internship.to.product.ProductWithId;
import com.simbirsoft.internship.util.exception.LowerThanAvaibleException;
import com.simbirsoft.internship.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.simbirsoft.internship.util.TosConverter.productWithIdCreate;

@Service
@Transactional(readOnly = true)
public class PurchaseServiceImpl implements PurchaseService {
    private PurchaseRepository purchaseRepository;
    private ProductRepository productRepository;
    private StoreRepository storeRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductRepository productRepository, StoreRepository storeRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional
    @Override
    public List<ProductWithId> makeAnPurchase(Purchase purchase) {
        // get Store where is purchase made
        StoreEntity store = storeRepository.getOne(purchase.getStoreId());
        // Products in Purchase
        List<ProductWithId> productsInPurchase = new ArrayList<>();
        // Products which available for purchase, and will not end after purchase
        Set<ProductEntity> purchasedProducts = new HashSet<>();
        // Products which available for purchase, and will end after this purchase
        List<ProductEntity> endedProducts = new ArrayList<>();
        // Products which not available for purchase in this quantity
        Map<ProductEntity, Integer> amountLowerThanAvailable = new HashMap<>();

        // total bill of the Purchase
        AtomicReference<Double> totalBill = new AtomicReference<>((double) 0);
        purchase.getPositions()
                .forEach(position -> {
                    // find Product by id, or null if not exist
                    int productId = position.getIdOfProduct();
                    ProductEntity product = productRepository.findById(productId)
                            .orElseThrow(() -> new NotFoundException("Not found Product with id=" + productId));

                    int amountOfProduct = product.getAmount();
                    int amountOfPurchasingProduct = position.getAmountOfProduct();
                    int available = amountOfProduct - amountOfPurchasingProduct;
                    // update availability of Product
                    if (available >= 0) {
                        ProductWithId productWithId = productWithIdCreate(product);
                        productWithId.setAmount(amountOfPurchasingProduct);
                        productsInPurchase.add(productWithId);

                        product.setAmount(available);
                        purchasedProducts.add(product);
                        totalBill.updateAndGet(v -> v + (product.getPrice() * amountOfPurchasingProduct));
                    }
                    if (available == 0) {
                        endedProducts.add(product);
                    }
                    if (available < 0) {
                        amountLowerThanAvailable.put(product, amountOfPurchasingProduct);
                        lowerThanAvailable(amountLowerThanAvailable);
                    }
                });

        if (!purchasedProducts.isEmpty()) {
            // saving data about purchase in DB
            purchaseRepository.save(
                    new PurchaseEntity(
                            null,
                            store,
                            purchasedProducts,
                            totalBill.get()));
            // updating Products quantities after purchasing
            productRepository.saveAll(purchasedProducts);
        }
        if (!endedProducts.isEmpty()) {
            // deleting Products which ended
            endedProducts.forEach(e->productRepository.delete(e));
        }
        return productsInPurchase;
    }

    private void lowerThanAvailable(Map<ProductEntity, Integer> amountLowerThanAvailable) {
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
        throw new LowerThanAvaibleException(sb.toString() + "Products can't be purchased. The quantity of products is lower than available. Change amount of ProductEntity or delete it from your request");
    }
}
