package com.transit.ticketing.repository;

import com.transit.ticketing.entity.TripInventory;
import com.transit.ticketing.entity.TripsInSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripInventoryRepository extends JpaRepository<TripInventory,Long> {
    @Query(value="Select * from trip_inventory as T join trip_inventory as S on T.Trip_id=S.Trip_id where T.stop_id=?1 and S.stop_id=?2 and S.stop_sequence>T.stop_sequence and cast(T.journey_date as Date)=CURDATE() and cast(S.journey_date as Date)=CURDATE()",nativeQuery = true)
    List<TripInventory> findTripsForGivenSourceAndDestination(String source,String destination);
}
