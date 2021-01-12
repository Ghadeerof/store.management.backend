package com.maids.cc.store.management.backend.entity;

import com.maids.cc.store.management.backend.model.BaseClass;
import com.maids.cc.store.management.backend.model.Person;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client extends Person {

    //region Properties
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Sale> saleSet;
    //endregion

    //region Constructors
    public Client() {
        this.saleSet = new HashSet<>();
    }

    public Client(String firstName, String lastName, String mobileNumber) {
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
    //endregion
}
