package com.transit.ticketing.controller;

import com.transit.ticketing.dto.BoatsDto;
import com.transit.ticketing.entity.Boats;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.service.BoatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoatController {
    private static final Logger LOG = LoggerFactory.getLogger(BoatController.class);
    @Autowired
    BoatService boatService;

    @GetMapping(value = "/api/v1/secure/boats")
    public ResponseEntity<List<BoatsDto>> search() throws ETicketingException {
        LOG.info("Received request: /api/v1/secure/boats");
        return ResponseEntity.ok().body(boatService.listAllBoats());
    }

    @GetMapping(value = "/api/v1/secure/boats/page={page}&records={records}")
    public ResponseEntity<List<BoatsDto>> search(@PathVariable int page, @PathVariable int records) throws ETicketingException {
        LOG.info("Received request: /api/v1/secure/boats/page={page}&records={records}", page, records);
        return ResponseEntity.ok().body(boatService.listAllBoats(page,records));
    }
}
