package com.maids.cc.store.management.backend.entity;

import com.maids.cc.store.management.backend.model.BaseClass;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Sale extends BaseClass {

    //region Properties
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SaleItem> saleItemSet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="seller_id")
    private Seller seller;
    //endregion

    //region Constructors
    public Sale() {
        saleItemSet = new HashSet<>();
    }
    //endregion

    //region Setters and Getters

    public Set<SaleItem> getSaleItemSet() {
        return saleItemSet;
    }

    public void setSaleItemSet(Set<SaleItem> saleItemSet) {
        this.saleItemSet = saleItemSet;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void addSaleItem(SaleItem saleItem){
        this.saleItemSet.add(saleItem);
    }

    public void removeSaleItem(SaleItem saleItem){
        this.saleItemSet.remove(saleItem);
    }
    //endregion

    //region Override methods
    @Override
    public String toString(){
        return "SaleID : " + this.getId()
                + ", ClientID : " + this.client.getId()
                + ", SellerID : " + this.seller.getId();
    }
    //endregion
}
