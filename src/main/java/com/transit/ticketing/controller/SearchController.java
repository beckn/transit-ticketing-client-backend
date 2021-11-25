package com.transit.ticketing.controller;

import com.transit.ticketing.dto.SearchTripDetailsDto;
import com.transit.ticketing.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @GetMapping(value = "/api/v1/search")
    public ResponseEntity<SearchTripDetailsDto> search(@RequestParam(required = true) String origin, @RequestParam(required = true) String destination) {
        LOG.info("Received request: /api/v1/search?origin={}&destination={}", origin, destination);
        return searchService.searchTrip(origin,destination);
    }
}
