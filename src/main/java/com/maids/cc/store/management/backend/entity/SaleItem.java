package com.maids.cc.store.management.backend.entity;

import com.maids.cc.store.management.backend.model.BaseClass;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SaleItem extends BaseClass {

    //region Properties
    private Integer quantity;

    private float price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sale_id")
    private Sale sale;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    //endregion

    //region Override methods
    @Override
    public String toString(){
        return "Sale Item ID : " + this.getId()
                + ", ProductID : " + this.product.getId()
                + ", Price : " + this.price
                + ", Quantity : " + this.quantity;
    }
    //endregion
}
