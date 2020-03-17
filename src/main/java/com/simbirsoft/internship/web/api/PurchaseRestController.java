package com.simbirsoft.internship.web.api;

import com.simbirsoft.internship.service.PurchaseService;
import com.simbirsoft.internship.to.Purchase;
import com.simbirsoft.internship.to.product.ProductWithId;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class PurchaseRestController {
    private PurchaseService service;

    @Autowired
    public PurchaseRestController(PurchaseService service) {
        this.service = service;
    }

    /**
     *
     * Make an order from Purchase data.
     *
     * @param purchase include positions of products which will be ordered.
     * @return list of purchased Products. If amount of available Products lower than amount in Purchase, will be LowerThanAvaibleException.
     */
    @ApiOperation(value = "Make a purchase", notes = "Input positions which need to order. " +
            "Each position have to contains id and quantity of Product. Purchase have to contains Store id.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public List<ProductWithId> makeAPurchase(@RequestBody Purchase purchase) {
        return service.makeAnPurchase(purchase);
    }
}
