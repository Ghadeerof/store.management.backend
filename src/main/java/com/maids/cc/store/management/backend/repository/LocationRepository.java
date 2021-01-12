package com.maids.cc.store.management.backend.repository;

import com.maids.cc.store.management.backend.entity.Client;
import com.maids.cc.store.management.backend.entity.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends CrudRepository<Location, UUID> {

    @Query(value = "SELECT l FROM Location l WHERE l.id = ?1")
    Location get(UUID id);

    @Query(value = "SELECT l FROM Location l")
    List<Location> getAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Location l WHERE l.id = ?1")
    void delete(UUID id);
}
