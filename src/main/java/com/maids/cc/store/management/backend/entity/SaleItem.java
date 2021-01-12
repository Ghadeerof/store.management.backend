package com.maids.cc.store.management.backend.entity;

import com.maids.cc.store.management.backend.model.BaseClass;

import javax.persistence.Entity;

@Entity
public class SaleItem extends BaseClass {

    //region Properties
    private Integer quantity;

    private float price;
    //endregion

    //region Constructors
    public SaleItem() {
    }

    public SaleItem(Integer quantity, float price) {
        this.quantity = quantity;
        this.price = price;
    }
    //endregion

    //region Setters and Getters
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    //endregion
}
