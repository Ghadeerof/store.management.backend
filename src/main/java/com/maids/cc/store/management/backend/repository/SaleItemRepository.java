package com.maids.cc.store.management.backend.repository;

import com.maids.cc.store.management.backend.entity.Product;
import com.maids.cc.store.management.backend.entity.SaleItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface SaleItemRepository extends CrudRepository<SaleItem, UUID> {

    @Query(value = "SELECT si FROM SaleItem si WHERE si.id = ?1")
    SaleItem get(UUID id);

    @Query(value = "SELECT si FROM SaleItem si")
    List<SaleItem> getAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM SaleItem si WHERE si.id = ?1")
    void delete(UUID id);

    @Query(value = "SELECT si FROM SaleItem si WHERE si.sale.id = ?1")
    List<SaleItem> getAllItemsOfSale(UUID saleId);

    @Query(value = "SELECT si FROM SaleItem si WHERE si.product.id = ?1")
    List<SaleItem> getAllSaleItemsOfProduct(UUID productId);
}
