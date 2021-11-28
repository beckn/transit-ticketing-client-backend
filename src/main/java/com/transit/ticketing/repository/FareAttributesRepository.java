package com.transit.ticketing.repository;

import com.transit.ticketing.entity.FareAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FareAttributesRepository extends JpaRepository<FareAttributes,Long> {
    @Query(value = "select * from fare_attributes where fare_id1=?1",nativeQuery = true)
    FareAttributes findByFareId(long fare_id);
}
