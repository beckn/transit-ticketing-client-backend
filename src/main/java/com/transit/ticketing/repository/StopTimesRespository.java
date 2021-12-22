package com.transit.ticketing.repository;

import com.transit.ticketing.entity.StopTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StopTimesRespository extends JpaRepository<StopTimes,Long> {
    @Query(value="select * from stop_times where stop_id=?1 and trip_id=?2",nativeQuery = true)
    StopTimes findStopTimeForStopIdAndTripId(long stop_id,long trip_id);
    @Query (value = "From StopTimes where tripId=?1 order by stop_sequence asc")
    List<StopTimes> findAllByTripId(long trip_id);
}
