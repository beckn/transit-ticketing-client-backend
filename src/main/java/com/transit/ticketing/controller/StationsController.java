package com.transit.ticketing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transit.ticketing.entity.Stations;
import com.transit.ticketing.service.StationService;

@RestController
public class StationsController {
    private static final Logger LOG = LoggerFactory.getLogger(StationsController.class);
    
    @Autowired
    private StationService stationService;

    @RequestMapping(value = "/api/v1/secure/stations", method = RequestMethod.GET)
 	 public ResponseEntity<Stations> getStation() {
    	        LOG.info("Received request: /api/v1/secure/getStations");
    	        return stationService.getStations();
    }
    
    @RequestMapping(value = "/api/v1/secure/stationsById", method = RequestMethod.GET)
    	 public ResponseEntity<Stations> getStationsById(@RequestParam(required = true) String origin) {
    	        LOG.info("Received request: /api/v1/stations?origin", origin);
    	        return stationService.getStationsById(origin);
    }
   
}
