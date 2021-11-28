package com.transit.ticketing.repository;

import com.transit.ticketing.entity.Boats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoatsRepository extends JpaRepository<Boats,Long> {
    @Query(value = "select * from boats where boat_id=?1",nativeQuery = true)
    Boats findByBoat_id(long boatId);
}
