package com.transit.ticketing.repository;

import com.transit.ticketing.entity.Stops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StopsRepository extends JpaRepository<Stops,Long> {
    @Query(value = "SELECT *, SQRT(\n" +
            "    POW(69.1 * (stop_lat - ?1), 2) +\n" +
            "    POW(69.1 * (?2 - stop_lon) * COS(stop_lat / 57.3), 2)) AS distance\n" +
            "FROM stops where active=1 ORDER BY distance asc limit 1", nativeQuery = true )
    Stops findStopsByGPS(String stopLat,String stopLon);
}
