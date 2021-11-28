package com.transit.ticketing.repository;

import com.transit.ticketing.entity.FareRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FareRulesRepository extends JpaRepository<FareRules,Long> {
    @Query(value="select * from fare_rules where origin_id = ?1 and destination_id = ?2",nativeQuery = true)
    FareRules findByOrigin_idAndDestination_id(long origin_id,long destination_id);
}
