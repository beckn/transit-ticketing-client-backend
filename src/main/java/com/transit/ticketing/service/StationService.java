package com.transit.ticketing.service;

import org.springframework.http.ResponseEntity;

import com.transit.ticketing.entity.Station;

import java.util.List;

public interface StationService{

	public ResponseEntity<List<Station>>  getStationsById(String stationId);

	public ResponseEntity<List<Station>>  getStationsByLocation(String lat, String lon);

}
