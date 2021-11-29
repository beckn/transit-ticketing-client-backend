package com.transit.ticketing.service.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.transit.ticketing.dto.LocationDetailsDto;
import com.transit.ticketing.dto.StationResponseDto;
import com.transit.ticketing.entity.Station;
import com.transit.ticketing.repository.StationRepository;
import com.transit.ticketing.service.StationService;

@Service
public class StationServiceImpl implements StationService {

	@Autowired
	StationRepository stationRepository;

	@Transactional
	public ResponseEntity<List<StationResponseDto>> getStationsById(String stopId) {

		List<Station> stations = new ArrayList();
		List<StationResponseDto> stationList = new ArrayList();

		if (stopId != null) {

			stations = stationRepository.findAllStationsById(stopId);
			
			for (Station station : stations) {
				
				StationResponseDto stationResponseDto = new StationResponseDto();
				LocationDetailsDto location = new LocationDetailsDto();

				stationResponseDto.setStopId(station.getStop_id());
				stationResponseDto.setStopName(station.getStop_name());

				location.setStopLat(station.getStop_lat());
				location.setStopLng(station.getStop_lon());
				stationResponseDto.setLocation(location);
				
				stationList.add( stationResponseDto);
			}	

		} else {

			stations = stationRepository.findAllStations();
			for (Station station : stations) {
				
				StationResponseDto stationResponseDto = new StationResponseDto();
				LocationDetailsDto location = new LocationDetailsDto();

				stationResponseDto.setStopId(station.getStop_id());
				stationResponseDto.setStopName(station.getStop_name());

				location.setStopLat(station.getStop_lat());
				location.setStopLng(station.getStop_lon());
				stationResponseDto.setLocation(location);
				
				stationList.add( stationResponseDto);
			}	

		}

		return ResponseEntity.status(HttpStatus.OK).body(stationList);

	}
}
