package com.maids.cc.store.management.backend.entity;

import com.maids.cc.store.management.backend.model.BaseClass;

import javax.persistence.Entity;

@Entity
public class Location extends BaseClass {

    //region Properties
    private String city;

    private String zone;

    private String street;
    //endregion

    //region Constructors
    public Location() {
    }

    public Location(String city, String zone, String street) {
        this.city = city;
        this.zone = zone;
        this.street = street;
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
    //endregion
}
