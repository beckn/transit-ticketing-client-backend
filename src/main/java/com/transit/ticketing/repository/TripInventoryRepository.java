package com.transit.ticketing.repository;

import com.transit.ticketing.entity.TripInventory;
import com.transit.ticketing.entity.TripsInSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripInventoryRepository extends JpaRepository<TripInventory,Long> {
    @Query(value="Select * from trip_inventory as T join trip_inventory as S on T.Trip_id=S.Trip_id where T.stop_id=?1 and S.stop_id=?2 and S.stop_sequence>T.stop_sequence and cast(T.journey_date as Date)=CURDATE() and cast(S.journey_date as Date)=CURDATE()",nativeQuery = true)
    List<TripInventory> findTripsForGivenSourceAndDestination(String source,String destination);
    @Query(value="Select * from trip_inventory as T join trip_inventory as S on T.Trip_id=S.Trip_id where T.stop_id=?1 and S.stop_id=?2 and S.stop_sequence>T.stop_sequence and T.journey_date like ?3% and S.journey_date like ?3% and T.trip_id=?4",nativeQuery = true)
    TripInventory findTripsForGivenSourceAndDestinationAndTripId(String source,String destination,String journeyDate,long tripId);
    @Query(value = "select stop_sequence from trip_inventory where trip_id=?1 and journey_date like ?2% and stop_id=?3",nativeQuery = true)
    int findStopSequence(long trip_id,String journeyDate,String stopId);
    @Query(value = "select max(issued_tickets) from trip_inventory where journey_date like ?2% and trip_id=?1 and stop_sequence>=?3 and stop_sequence<?4",nativeQuery = true)
    int findIssuedTickets(long tripId,String journeyDate,int sourceStopSequence,int destinationStopSequence);

    @Modifying
    @Query(value = "update trip_inventory set issued_tickets = issued_tickets+?5  where trip_id=?1 and journey_date like ?2% and stop_sequence >=?3 and stop_sequence <?4",nativeQuery = true)
    int updateIssuedTicketCount(long tripId,String journeyDate,int sourceStopSequence,int destinationStopSequence,int numberOfTickets);

    List<TripInventory> findAllByTripId(Long tripId);
}
