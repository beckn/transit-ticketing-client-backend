package com.transit.ticketing.repository;

import com.transit.ticketing.entity.StopTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StopTimesRespository extends JpaRepository<StopTimes,Long> {
    @Query(value="select * from stop_times where stop_id=?1 and trip_id=?2",nativeQuery = true)
    StopTimes findStopTimeForStopIdAndTripId(long stop_id,long trip_id);
}
