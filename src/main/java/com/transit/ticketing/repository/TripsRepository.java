package com.transit.ticketing.repository;

import com.transit.ticketing.entity.Trips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripsRepository extends JpaRepository<Trips,Long> {

}
