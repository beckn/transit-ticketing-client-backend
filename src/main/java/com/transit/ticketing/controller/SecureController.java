package com.transit.ticketing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {
    private static final Logger LOG = LoggerFactory.getLogger(SecureController.class);

    @GetMapping(value = "/api/v1/secure")
    public ResponseEntity secure() {
        LOG.info("Received request: /api/v1/secure");
        return ResponseEntity.ok().build();
    }
}
