package com.maids.cc.store.management.backend.repository;

import com.maids.cc.store.management.backend.entity.Client;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID> {

    @Query(value = "SELECT c FROM Client c WHERE c.id = ?1")
    Client get(UUID id);

    @Query(value = "SELECT c FROM Client c")
    List<Client> getAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Client c WHERE c.id = ?1")
    void delete(UUID id);
}
