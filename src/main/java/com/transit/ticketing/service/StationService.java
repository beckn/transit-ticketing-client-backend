package com.transit.ticketing.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.transit.ticketing.entity.Station;

public interface StationService {

	public ResponseEntity<List<Station>> getStationsById(String stationId);

}
