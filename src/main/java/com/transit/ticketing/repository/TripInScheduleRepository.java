package com.transit.ticketing.repository;

import com.transit.ticketing.entity.TripsInSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripInScheduleRepository extends JpaRepository<TripsInSchedule,Long> {
    @Query(name="select schedule_id from trip_in_schedule where trip_id = ?1",nativeQuery = true)
    TripsInSchedule findByTripId(long tripId);
}
