package com.transit.ticketing.service.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.transit.ticketing.entity.Station;
import com.transit.ticketing.service.StationService;
import com.transit.ticketing.util.StationMockData;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService{

	@Override
	public ResponseEntity<List<Station>> getStationsById(String origin) {

		List<Station> stations = new ArrayList();
		if(origin != null && !origin.isEmpty()){
			//Search by Origin
			stations = StationMockData.populateStationData(origin);
		}else{
			stations = StationMockData.populateStationData();
		}
		return ResponseEntity.status(HttpStatus.OK).body(stations);
	}

	@Override
	public ResponseEntity<List<Station>> getStationsByLocation(String lat, String lon) {

		//TODO implement backend query to fetch data from db
		List<Station> stations = StationMockData.populateStationData();

		return ResponseEntity.status(HttpStatus.OK).body(stations);
	}

}
