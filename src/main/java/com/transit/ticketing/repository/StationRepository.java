package com.transit.ticketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.transit.ticketing.entity.Station;

public interface StationRepository extends JpaRepository<Station, String> {

	@Query(value = "SELECT STOP_ID,STOP_NAME, STOP_LAT, STOP_LON FROM STOPS", nativeQuery = true)
	List<Station> findAllStations();

	@Query(value = "SELECT STOP_ID,STOP_NAME, STOP_LAT, STOP_LON FROM STOPS"
					+ " WHERE STOP_ID IN ("
						+ "SELECT DISTINCT DESTINATION.STOP_ID FROM STOP_TIMES ORIGIN, STOP_TIMES DESTINATION "
							+ "WHERE ORIGIN.TRIP_ID = DESTINATION.TRIP_ID AND ORIGIN.STOP_SEQUENCE < DESTINATION.STOP_SEQUENCE "
							+ "AND ORIGIN.STOP_ID = ?1)", nativeQuery = true)
	List<Station> findAllStationsById(String stopId);

}
