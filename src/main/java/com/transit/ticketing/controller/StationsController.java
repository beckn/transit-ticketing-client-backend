package com.transit.ticketing.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transit.ticketing.dto.StationResponseDto;
import com.transit.ticketing.entity.Station;
import com.transit.ticketing.service.StationService;

@RestController
public class StationsController {
	private static final Logger LOG = LoggerFactory.getLogger(StationsController.class);

	@Autowired
	private StationService stationService;

	@RequestMapping(value = "/api/v1/secure/stations", method = RequestMethod.GET)
	public ResponseEntity<List<StationResponseDto>> getStationsById(@RequestParam(required = false) String origin) {
		if(origin == null) {
			LOG.info("Received request: /api/v1/stations");
		}else
			LOG.info("Received request: /api/v1/stations?origin", origin);
		return stationService.getStationsById(origin);
	}

}
