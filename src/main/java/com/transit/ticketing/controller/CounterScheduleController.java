package com.transit.ticketing.controller;

import com.transit.ticketing.dto.BoatScheduleResponseDto;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.service.BoatScheduleService;
import com.transit.ticketing.service.CounterScheduleService;
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
public class CounterScheduleController {
	private static final Logger LOG = LoggerFactory.getLogger(CounterScheduleController.class);

	@Autowired
	private CounterScheduleService counterScheduleService;

	@RequestMapping(value = "/api/v1/secure/counter/schedules", method = RequestMethod.GET)
	public ResponseEntity<List<BoatScheduleResponseDto>> getCounterSchedules(@RequestParam(required = false) Long counterId) throws ETicketingException {
		if(counterId == null) {
			LOG.info("Received request: /api/v1/counter/schedules");
			return counterScheduleService.findAllCountersSchedule();
		}else
			LOG.info("Received request: /api/v1/counter/schedules?counterIdl", counterId);
		return counterScheduleService.findCounterSchedule(counterId);

	}

}
