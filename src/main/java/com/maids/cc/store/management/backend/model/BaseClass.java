package com.maids.cc.store.management.backend.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@MappedSuperclass
public class BaseClass {

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column( columnDefinition = "BINARY(16)" )
    private UUID createdBy;

    @Column( columnDefinition = "BINARY(16)" )
    private UUID deletedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    private Timestamp deletedAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
    //endregion

    //region Getters & Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(UUID deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    //endregion
}
