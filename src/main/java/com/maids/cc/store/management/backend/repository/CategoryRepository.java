package com.maids.cc.store.management.backend.repository;

import com.maids.cc.store.management.backend.entity.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {

    @Query(value = "SELECT c FROM Category c WHERE c.id = ?1")
    Category get(UUID id);

    @Query(value = "SELECT c FROM Category c")
    List<Category> getAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Category c WHERE c.id = ?1")
    void delete(UUID id);
}
