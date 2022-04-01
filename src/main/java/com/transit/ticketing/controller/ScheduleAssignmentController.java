package com.transit.ticketing.controller;

import com.transit.ticketing.dto.BoatScheduleResponseDto;
import com.transit.ticketing.dto.ScheduleAssignmentRequestDto;
import com.transit.ticketing.dto.ScheduleAssignmentResponseDto;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.service.ScheduleJourneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleAssignmentController {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleAssignmentController.class);

    @Autowired
    ScheduleJourneyService scheduleJourneyService;

    @RequestMapping(value = "/api/v1/secure/schedule/assignments/save", method = RequestMethod.POST)
    public ResponseEntity<ScheduleAssignmentResponseDto> saveAssignment(@RequestBody ScheduleAssignmentRequestDto assignmentRequestDto) throws ETicketingException {
        LOG.info("Received request: /api/v1/secure/schedule/assignments/save");
        return ResponseEntity.ok().body(scheduleJourneyService.saveAssignment(assignmentRequestDto));
    }
}
