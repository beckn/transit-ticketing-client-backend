package com.transit.ticketing.controller;

import com.transit.ticketing.dto.BoatScheduleResponseDto;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.service.BoatScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoatScheduleController {
	private static final Logger LOG = LoggerFactory.getLogger(BoatScheduleController.class);

	@Autowired
	private BoatScheduleService boatScheduleService;

	@RequestMapping(value = "/api/v1/secure/boat/schedules", method = RequestMethod.GET)
	public ResponseEntity<List<BoatScheduleResponseDto>> getBoatSchedules(@RequestParam(required = false) Long boatId) throws ETicketingException {
		if(boatId == null) {
			LOG.info("Received request: /api/v1/boat/schedules");
			return boatScheduleService.findAllScheduleBoats();
		}else
			LOG.info("Received request: /api/v1/boat/schedules?boatId", boatId);
		return boatScheduleService.findScheduleBoat(boatId);

	}

}
