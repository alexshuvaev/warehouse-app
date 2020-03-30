package com.simbirsoft.internship.web.api;

import com.simbirsoft.internship.dto.ConfirmedPurchase;
import com.simbirsoft.internship.dto.Purchase;
import com.simbirsoft.internship.entity.PurchaseEntity;
import com.simbirsoft.internship.service.PurchaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.simbirsoft.internship.util.DTOsConverter.purchaseToConfirmCreate;

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
    @ApiOperation(value = "Make a purchase", notes = "Input positions which need dto order. " +
            "Each position have dto contains id and quantity of Product. Purchase have dto contains Store id.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public ConfirmedPurchase makeAPurchase(@RequestBody Purchase purchase) {
        PurchaseEntity purchaseEntity = service.makeAnPurchase(purchase);
        return purchaseToConfirmCreate(purchaseEntity);
    }
}
