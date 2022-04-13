package com.transit.ticketing.repository;

import com.transit.ticketing.entity.SalesRecords;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface SalesRecordsRepository extends JpaRepository<SalesRecords,Long> {
    @Query(value = "select * from sales_records where ?1 < destination_jetty and trip_id=?2 and boat_id=?3 and schedule_id = ?4 and date_of_journey like ?5%",nativeQuery = true)
    List<SalesRecords> issuesTicketsCount(long destination, long trip_id, long boat_id, long schedule_id, String date_of_journey);

    @Query(value = "select * from sales_records where order_id=?1",nativeQuery = true)
    SalesRecords findSalesRecordsWithOrderId(long order_id);

    @Modifying
    @Query(value="update sales_records set status = ?1 where order_id = ?2",nativeQuery = true)
    int setStatusForSalesRecords(String status, Long id);

    @Modifying
    @Query(value="update sales_records set signature = ?1 where order_id = ?2",nativeQuery = true)
    int setSignatureForSalesRecords(String signature, Long id);

    @Query(value = "select * from sales_records where trip_id=?1 and boat_id=?2",nativeQuery = true)
    List<SalesRecords> findAllByTripIdAndBoatId(Long tripId, Long boatId);

    @Query(value = "select * from sales_records order by date_of_journey desc", nativeQuery = true)
    List<SalesRecords> findAllSalesRecord();
}
