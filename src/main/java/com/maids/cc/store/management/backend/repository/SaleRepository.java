package com.maids.cc.store.management.backend.repository;

import com.maids.cc.store.management.backend.entity.Sale;
import com.maids.cc.store.management.backend.entity.SaleItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends CrudRepository<Sale, UUID> {

    @Query(value = "SELECT s FROM Sale s WHERE s.id = ?1")
    Sale get(UUID id);

    @Query(value = "SELECT s FROM Sale s")
    List<Sale> getAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Sale s WHERE s.id = ?1")
    void delete(UUID id);

    @Query(value = "SELECT s FROM Sale s WHERE s.client.id = ?1")
    List<Sale> getAllSalesOfClient(UUID clientId);

    @Query(value = "SELECT s FROM Sale s WHERE s.seller.id = ?1")
    List<Sale> getAllSalesOfSeller(UUID sellerId);
}
