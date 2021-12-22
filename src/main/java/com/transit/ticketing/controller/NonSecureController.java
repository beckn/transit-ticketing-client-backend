package com.transit.ticketing.controller;

import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.service.ScheduledTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NonSecureController {
    private static final Logger LOG = LoggerFactory.getLogger(NonSecureController.class);

    @Autowired
    ScheduledTask scheduledTask;

    @GetMapping(value = "/api/v1/nonsecure")
    public ResponseEntity nonsecure() {
        LOG.info("Received request: /api/v1/nonsecure");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/api/v1/nonsecure/triggercron")
    public ResponseEntity triggerCron() throws ETicketingException {
        LOG.info("Received request: /api/v1/nonsecure/triggercron");
        scheduledTask.createJourney();
        return ResponseEntity.ok().build();
    }
}
