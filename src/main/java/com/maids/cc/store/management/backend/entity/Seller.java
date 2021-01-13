package com.maids.cc.store.management.backend.entity;

import com.maids.cc.store.management.backend.model.BaseClass;
import com.maids.cc.store.management.backend.model.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Seller extends Person {

    //region Properties
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Sale> saleSet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="location_id")
    private Location location;
    //endregion

    //region Constructors
    public Seller() {
        this.saleSet = new HashSet<>();
    }

    public Seller(String firstName, String lastName, String mobileNumber) {
        super(firstName, lastName, mobileNumber);
        this.saleSet = new HashSet<>();
    }
    //endregion

    //region Setters and Getters

    public Set<Sale> getSaleSet() {
        return saleSet;
    }

    public void setSaleSet(Set<Sale> saleSet) {
        this.saleSet = saleSet;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addSale(Sale sale){
        this.saleSet.add(sale);
    }

    public void removeSale(Sale sale){
        this.saleSet.remove(sale);
    }
    //endregion
}
