package com.simbirsoft.internship.web.api;

import com.simbirsoft.internship.service.ProductService;
import com.simbirsoft.internship.to.Invoice;
import com.simbirsoft.internship.to.product.Product;
import com.simbirsoft.internship.to.product.ProductWithId;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.simbirsoft.internship.util.TosConverter.*;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {
    private ProductService service;

    @Autowired
    public ProductRestController(ProductService service) {
        this.service = service;
    }

    /**
     * Getting Product by id.
     *
     * @param id of the getting Product.
     * @return Product entity, if not null. If Product=null will be NotFoundException.
     */
    @ApiOperation(value = "Find Product by id", notes = "Provide an id to get single Product from DB")
    @GetMapping("/{id}")
    public ProductWithId get(@PathVariable Integer id) {
        return (ProductWithId) productCreate(service.findById(id));
    }

    /**
     * Create List of Products by Invoice to DB.
     *
     * @param invoice with data of Products.
     * @return List of saved Products.
     */
    @ApiOperation(value = "Create list of Products", notes = "Add Products from Invoice")
    @PostMapping("")
    public List<Product> createList(@RequestBody Invoice invoice) {
        return productsListCreate(service.createList(invoice.getProducts()));
    }
}
