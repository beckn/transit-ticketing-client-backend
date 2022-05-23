package com.transit.ticketing.controller;

import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.repository.ScheduleRepository;
import com.transit.ticketing.repository.StationRepository;
import com.transit.ticketing.service.BoatService;
import com.transit.ticketing.service.StaffService;
import com.transit.ticketing.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BoatAssignmentsController {
    private static final Logger LOG = LoggerFactory.getLogger(BoatAssignmentsController.class);

    @Autowired
    BoatService boatService;

    @Autowired
    StationRepository stationRepository;

    @Autowired
    StaffService staffService;

    @Autowired
    ScheduleRepository scheduleRepository;

    @RequestMapping(value = "/api/v1/secure/boat/assignments", method = RequestMethod.GET)
    public ResponseEntity<Object> getBoatAssignments(@RequestParam(required = false) Long boatId) throws ETicketingException {
        if (boatId == null) {
            LOG.info("Received request: /api/v1/boat/assignments");
            Map<String, Object> assignments = new HashMap<>();

            assignments.put("boats", boatService.listAllBoats());
            assignments.put("stations", stationRepository.findAllStations());
            assignments.put("boatMasters", staffService.listAllStaff());
            assignments.put("schedules", scheduleRepository.findAllActiveSchedules());

            return ResponseEntity.ok(assignments);
        } else {
            LOG.info("Received request: /api/v1/boat/assignments?boatId", boatId);
            return null;
        }
    }

    }
