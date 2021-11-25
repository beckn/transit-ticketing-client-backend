package com.transit.ticketing.service.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.transit.ticketing.entity.Location;
import com.transit.ticketing.entity.Stations;
import com.transit.ticketing.service.StationService;
import com.transit.ticketing.util.StationMockData;

@Service
public class StationServiceImpl implements StationService{

	@Override
	public ResponseEntity<Stations> getStations() {
		
		//TODO implement backend query to fetch data from db
		Stations stations = StationMockData.populateStationData();
	    return ResponseEntity.status(HttpStatus.OK).body(stations);
	}

	@Override
	public ResponseEntity<Stations> getStationsById(String stationId) {
		
		//TODO implement backend query to fetch data from db
		Stations stations = StationMockData.populateStationDataById(stationId); 

	    return ResponseEntity.status(HttpStatus.OK).body(stations);
	}

	@Override
	public ResponseEntity<Stations> getStationsByLocation(String lat, String lon) {

		//TODO implement backend query to fetch data from db
				Stations stations = StationMockData.populateStationDataByLocation(lat,lon); 

		return ResponseEntity.status(HttpStatus.OK).body(stations);
	}

}
