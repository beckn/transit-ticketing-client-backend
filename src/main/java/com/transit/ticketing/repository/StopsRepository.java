package com.transit.ticketing.repository;

import com.transit.ticketing.entity.Stops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StopsRepository extends JpaRepository<Stops,Long> {
    @Query(value = "SELECT *, SQRT(\n" +
            "    POW(69.1 * (stop_lat - ?1), 2) +\n" +
            "    POW(69.1 * (?2 - stop_lon) * COS(stop_lat / 57.3), 2)) AS distance\n" +
            "FROM stops where active=1 ORDER BY distance asc limit 1", nativeQuery = true )
    Stops findStopsByGPS(String stopLat,String stopLon);

    @Query(value = "SELECT * FROM stops where counter_present = 1",nativeQuery = true)
    List<Stops> findAllCounterStops();

    @Query(value = "SELECT * FROM stops where counter_present = 1 AND stop_id = ?1",nativeQuery = true)
    List<Stops> findStopsById(long stopId);

    @Query(value = "SELECT * FROM stops where stop_id = ?1", nativeQuery = true)
    Stops findAllByStopId(long stopId);
}
