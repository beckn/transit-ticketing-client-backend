package com.transit.ticketing.repository;

import com.transit.ticketing.entity.Staff;
import com.transit.ticketing.entity.Stops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findByRole(String role);

    @Query(value = "SELECT * FROM staff where role = 'Boatmaster'",nativeQuery = true)
    List<Staff> findAllBoatMasters();

    @Query(value = "SELECT * FROM staff where role = 'Ticketmaster'",nativeQuery = true)
    List<Staff> findAllTicketMasters();
}
