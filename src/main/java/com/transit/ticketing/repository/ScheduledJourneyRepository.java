package com.transit.ticketing.repository;

import com.transit.ticketing.entity.ScheduledJourney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ScheduledJourneyRepository extends JpaRepository<ScheduledJourney,Long> {
    @Query(value = "select * from scheduled_journey where schedule_id = ?1",nativeQuery = true)
    ScheduledJourney fetchScheduled(long scheduledId, String jouneyDate);

    @Query(value = "select * from scheduled_journey where boat_id = ?1",nativeQuery = true)
    List<ScheduledJourney> fetchScheduleBoat(long boatId);

    @Query(value = "select * from scheduled_journey",nativeQuery = true)
    List<ScheduledJourney> fetchAllScheduleBoats();

    @Query(value = "select * from scheduled_journey where schedule_id in (select schedule_id from trips_in_schedule where trip_id = ?1) ",nativeQuery = true)
    List<ScheduledJourney> fetchAllScheduleJourneyForTrip(long trip_id);


}
