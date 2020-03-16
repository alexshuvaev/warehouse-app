package com.simbirsoft.internship.web.api;

import com.simbirsoft.internship.entity.CategoryEntity;
import com.simbirsoft.internship.service.CategoryService;
import com.simbirsoft.internship.to.category.Category;
import com.simbirsoft.internship.to.category.CategoryWithId;
import com.simbirsoft.internship.to.category.CategoryWithProducts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.simbirsoft.internship.util.TosConverter.*;

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
    @GetMapping("/all")
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
    @ApiOperation(value = "Find Category by id", notes = "Provide an id to get single Category from DB")
    @GetMapping("/{id}")
    public CategoryWithProducts get(@PathVariable int id) {
        CategoryEntity category = service.findById(id);
        return categoryWithProductsCreate(category);
    }

    /**
     * Create single Category.
     *
     * @param category Category which will be saved if name is unique.
     * @return Category entity. if Category name not unique will return MustBeUniqueException.
     */
    @ApiOperation(value = "Create single Category", notes = "Provide new name for Category. Only single Category can be create in request.")
    @PostMapping("")
    public CategoryWithId create(@RequestBody Category category) {
        CategoryEntity categoryEntity = service.create(new CategoryEntity(null, category.getName()));
        return categoryWithIdCreate(categoryEntity);
    }

    /**
     * Create list of Categories.
     *
     * @param names Elements of array which will become Category entities and savedAll in the DB.
     *              Only unique Elements will be saved.
     * @return List of saved unique Categories.
     */
    @ApiOperation(value = "Create a list of Categories", notes = "Input an array of names to create are multiple categories.")
    @PostMapping("/all")
    public List<CategoryWithId> createList(@RequestBody String... names) {
        return service.createList(Arrays.asList(names));
    }

    /**
     * Update existing Category.
     *
     * @param category contains new name of Category.
     * @param id of Category which will be updated if Category exist.
     * @return updated Category. If Category with this id not exist, will be NotFoundException.
     */
    @ApiOperation(value = "Update name of single Category", notes = "Provide new name for Category. Only single Category can be updated in request.")
    @PutMapping("/{id}")
    public CategoryWithId update(@RequestBody Category category, @PathVariable int id) {
        CategoryEntity categoryEntity = service.update(new CategoryEntity(id, category.getName()));
        return categoryWithProductsCreate(categoryEntity);
    }
}
