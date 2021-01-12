package com.maids.cc.store.management.backend.repository;

import com.maids.cc.store.management.backend.entity.Sale;
import com.maids.cc.store.management.backend.entity.Seller;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface SellerRepository extends CrudRepository<Seller, UUID> {

    @Query(value = "SELECT s FROM Seller s WHERE s.id = ?1")
    Seller get(UUID id);

    @Query(value = "SELECT s FROM Seller s")
    List<Seller> getAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Seller s WHERE s.id = ?1")
    void delete(UUID id);

    @Query(value = "SELECT s FROM Seller s WHERE s.location.street = ?1")
    List<Seller> getAllSellersByStreet(String street);
}
