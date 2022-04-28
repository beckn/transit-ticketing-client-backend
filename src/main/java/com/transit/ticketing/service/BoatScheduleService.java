package com.transit.ticketing.service;

import com.transit.ticketing.exception.ETicketingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BoatScheduleService {
    ResponseEntity<Object> findScheduleBoat(Long boatId) throws ETicketingException;
    ResponseEntity<Object> findAllScheduleBoats() throws ETicketingException;
}
