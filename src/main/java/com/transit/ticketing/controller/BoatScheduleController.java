package com.transit.ticketing.controller;

import com.transit.ticketing.dto.BoatScheduleDto;
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

import java.util.ArrayList;
import java.util.List;

@RestController
public class BoatScheduleController {
	private static final Logger LOG = LoggerFactory.getLogger(BoatScheduleController.class);

	@Autowired
	private BoatScheduleService boatScheduleService;

	@RequestMapping(value = "/api/v1/secure/boat/schedules", method = RequestMethod.GET)
	public ResponseEntity<List<BoatScheduleDto>> getBoatSchedules(@RequestParam(required = false) Long boatId) throws ETicketingException {
		if(boatId == null) {
			LOG.info("Received request: /api/v1/boat/schedules");
			return boatScheduleService.findAllScheduleBoats();
		}else
			LOG.info("Received request: /api/v1/boat/schedules?boatId", boatId);
		return boatScheduleService.findScheduleBoat(boatId);
		//return boatScheduleService.findScheduleBoat(boatId);

		/*List<BoatScheduleDto> boatScheduleDtos = new ArrayList<>();
		BoatScheduleDto boatScheduleDto = new BoatScheduleDto();
		boatScheduleDto.setBoatNo("1102");
		boatScheduleDto.setBoatMasterName("Navjeet Singh");
		boatScheduleDto.setStartTime("11:02 AM");
		boatScheduleDto.setEndTime("12:02 AM");
		boatScheduleDto.setStartLocation("EDATHUA");
		boatScheduleDto.setEndLocation("NEDUMUDY");

		BoatScheduleDto boatScheduleDto2 = new BoatScheduleDto();
		boatScheduleDto2.setBoatNo("1103");
		boatScheduleDto2.setBoatMasterName("Navjeet Singh");
		boatScheduleDto2.setStartTime("11:02 AM");
		boatScheduleDto2.setEndTime("12:02 AM");
		boatScheduleDto2.setStartLocation("EDATHUA");
		boatScheduleDto2.setEndLocation("NEDUMUDY");

		boatScheduleDtos.add(boatScheduleDto);
		boatScheduleDtos.add(boatScheduleDto2);

		return ResponseEntity.ok().body(boatScheduleDtos);*/
	}

}
