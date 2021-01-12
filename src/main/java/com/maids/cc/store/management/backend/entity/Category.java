package com.maids.cc.store.management.backend.entity;

import com.maids.cc.store.management.backend.model.BaseClass;

import javax.persistence.Entity;

@Entity
public class Category extends BaseClass {

    //region Properties
    private String name;

    private String description;
    //endregion

    //region Constructors
    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
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
    //endregion
}
