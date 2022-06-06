package com.transit.ticketing.repository;

import com.transit.ticketing.entity.Boats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoatsRepository extends JpaRepository<Boats,Long> {
    @Query(value = "select * from boats where boat_id=?1",nativeQuery = true)
    Boats findByBoat_id(long boatId);

    @Query(value = "select staff_name from staff where staff_id=(select boatmaster from scheduled_journey where boat_id=?1)",nativeQuery = true)
    String findBoatMasterByBoat_id(long boatId);

    @Query(value = "select schedule_name from schedule where schedule_id = (select schedule_id from scheduled_journey where boat_id=?1)",nativeQuery = true)
    String findScheduleNameByBoat_id(long boatId);

    @Query(value = "select * from boats where station_id=?1",nativeQuery = true)
    Boats findByStationId(long station_id);

    @Query(value = "select * from boats where boat_reg_no=?1",nativeQuery = true)
    Boats findByBoatRegNo(String boatNumber);
}
