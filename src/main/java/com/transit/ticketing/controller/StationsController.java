package com.transit.ticketing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transit.ticketing.entity.Station;
import com.transit.ticketing.service.StationService;

import java.util.List;

@RestController
public class StationsController {
    private static final Logger LOG = LoggerFactory.getLogger(StationsController.class);
    
    @Autowired
    private StationService stationService;


    @RequestMapping(value = "/api/v1/secure/stations", method = RequestMethod.GET)
    	 public ResponseEntity<List<Station>> getStationsById(@RequestParam(required = false) String origin) {
    	        LOG.info("Received request: /api/v1/stations?origin", origin);
    	        return stationService.getStationsById(origin);
    }
    
    @RequestMapping(value = "/api/v1/secure/stationsByLocation", method = RequestMethod.GET)
	 public ResponseEntity<List<Station>> getStationsByLocation(@RequestParam(required = true) String lat, @RequestParam(required = true) String lon) {
	        LOG.info("Received request: /api/v1/stations?origin", lat,lon);
	        return stationService.getStationsByLocation(lat,lon);
    }
   
}
