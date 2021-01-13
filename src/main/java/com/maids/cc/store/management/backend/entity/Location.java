package com.maids.cc.store.management.backend.entity;

import com.maids.cc.store.management.backend.model.BaseClass;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Location extends BaseClass {

    //region Properties
    private String city;

    private String zone;

    private String street;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Seller> sellerSet;
    //endregion

    //region Constructors
    public Location() {
        this.sellerSet = new HashSet<>();
    }

    public Location(String city, String zone, String street) {
        this.city = city;
        this.zone = zone;
        this.street = street;
        this.sellerSet = new HashSet<>();
    }
    //endregion

    //region Properties
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Set<Seller> getSellerSet() {
        return sellerSet;
    }

    public void setSellerSet(Set<Seller> sellerSet) {
        this.sellerSet = sellerSet;
    }

    public void addSeller(Seller seller){
        this.sellerSet.add(seller);
    }

    public void removeSeller(Seller seller){
        this.sellerSet.remove(seller);
    }
    //endregion
}
