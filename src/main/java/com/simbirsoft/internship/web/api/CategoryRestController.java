package com.simbirsoft.internship.web.api;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.service.CategoryService;
import com.simbirsoft.internship.dto.category.Category;
import com.simbirsoft.internship.dto.category.CategoryWithId;
import com.simbirsoft.internship.dto.category.CategoryWithProducts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.simbirsoft.internship.util.DTOsConverter.*;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    private CategoryService service;

    @Autowired
    public CategoryRestController(CategoryService service) {
        this.service = service;
    }

    /**
     * Getting Categories list.
     *
     * @return list of Categories without showing products (only amount of products in each Category).
     */
    @ApiOperation(value = "Find all Categories", notes = "Find all Categories from DB. Only Categories ids and names, without displaying Products")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all")
    public List<CategoryWithId> getAll() {
        List<CategoryEntity> categoryEntityList =  service.findAll();
        return categoryWithIdListCreate(categoryEntityList);
    }

    /**
     * Getting Category with Product list by id.
     *
     * @param id of the getting Category.
     * @return Category entity with Product entities, if Category not null. If Category=null will be NotFoundException.
     */
    @ApiOperation(value = "Find Category by id", notes = "Provide an id dto get single Category from DB")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CategoryWithProducts get(@PathVariable int id) {
        CategoryEntity category = service.findById(id);
        return categoryWithProductsCreate(category);
    }

    /**
     * Create single Category.
     *
     * @param category Category which will be saved if name is unique.
     * @return Category entity. if Category name not unique will return InvalidPropertyException.
     */
    @ApiOperation(value = "Create single Category", notes = "Provide new name for Category. Only single Category can be create in request.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public CategoryWithId create(@RequestBody Category category) {
        CategoryEntity categoryEntity = service.create(new CategoryEntity(null, category.getName()));
        return categoryWithIdCreate(categoryEntity);
    }

    /**
     * Update existing Category.
     *
     * @param category contains new name of Category.
     * @param id of Category which will be updated if Category exist.
     * @return updated Category. If Category with this id not exist, will be NotFoundException.
     */
    @ApiOperation(value = "Update name of single Category", notes = "Provide new name for Category. Only single Category can be updated in request.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CategoryWithId update(@RequestBody Category category, @PathVariable int id) {
        CategoryEntity categoryEntity = service.update(new CategoryEntity(id, category.getName()));
        return categoryWithProductsCreate(categoryEntity);
    }
}
