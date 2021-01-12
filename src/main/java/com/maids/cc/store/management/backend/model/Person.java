package com.maids.cc.store.management.backend.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person extends BaseClass{

    //region Properties
    private String firstName;

    private String lastName;

    private String mobileNumber;
    //endregion

    //region Constructor
    public Person() {
    }

    public Person(String firstName, String lastName, String mobileNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
    }
    //endregion

    //region Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    //endregion
}
