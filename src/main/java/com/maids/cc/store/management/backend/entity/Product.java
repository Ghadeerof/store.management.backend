package com.maids.cc.store.management.backend.entity;

import com.maids.cc.store.management.backend.model.BaseClass;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product extends BaseClass {

    //region Properties
    private String name;

    private String description;

    private float price;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SaleItem> saleItemSet;
    //endregion

    //region Constructors
    public Product() {
        this.saleItemSet = new HashSet<>();
    }

    public Product(String name, String description, float price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.saleItemSet = new HashSet<>();
    }
    //endregion

    //region Setters and Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<SaleItem> getSaleItemSet() {
        return saleItemSet;
    }

    public void setSaleItemSet(Set<SaleItem> saleItemSet) {
        this.saleItemSet = saleItemSet;
    }

    //endregion
}
