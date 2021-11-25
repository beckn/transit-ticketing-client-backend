package com.transit.ticketing.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.transit.ticketing.entity.Stations;

public interface StationService{

	public ResponseEntity<Stations> getStations();
	
	public ResponseEntity<Stations> getStationsById(String stationId);

	public ResponseEntity<Stations> getStationsByLocation(String lat, String lon);

}
