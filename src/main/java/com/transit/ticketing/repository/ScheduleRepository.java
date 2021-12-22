package com.transit.ticketing.repository;

import com.transit.ticketing.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Query(value = "From Schedule where active=1")
    List<Schedule> findAllActiveSchedules();
}
