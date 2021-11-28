package com.transit.ticketing.repository;

import com.transit.ticketing.entity.ScheduledJourney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ScheduledJourneyRepository extends JpaRepository<ScheduledJourney,Long> {
    @Query(value = "select * from scheduled_journey where schedule_id = ?1 and journey_date like ?2%",nativeQuery = true)
    ScheduledJourney fetchScheduled(long scheduledId, String jouneyDate);
}
