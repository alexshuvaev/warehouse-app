package com.simbirsoft.internship.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity extends AbstractNamedEntity{
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    private Set<ProductEntity> products = new HashSet<>();

    public CategoryEntity() {
    }

    public CategoryEntity(Integer id, String name) {
        super(id, name);
        this.products = Collections.emptySet();
    }

    public Set<ProductEntity> getProducts() {
                return products;
    }

    public void setProducts(Set<ProductEntity> productEntitySet) {
        this.products = productEntitySet;
    }

    @Override
    public String toString() {
        return getId() + " " + getName();
    }
}
