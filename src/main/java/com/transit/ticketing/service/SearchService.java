package com.transit.ticketing.service;

import com.transit.ticketing.dto.SearchTripDetailsDto;
import com.transit.ticketing.exception.ETicketingException;
import org.springframework.http.ResponseEntity;

public interface SearchService {

  ResponseEntity<SearchTripDetailsDto> searchTrip(String origin, String destination) throws ETicketingException;

}
