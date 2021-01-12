package com.maids.cc.store.management.backend.repository;

import com.maids.cc.store.management.backend.entity.Location;
import com.maids.cc.store.management.backend.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    @Query(value = "SELECT p FROM Product p WHERE p.id = ?1")
    Product get(UUID id);

    @Query(value = "SELECT p FROM Product p")
    List<Product> getAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = ?1")
    void delete(UUID id);
}
