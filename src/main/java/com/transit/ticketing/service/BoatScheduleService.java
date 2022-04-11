package com.transit.ticketing.service;

import com.transit.ticketing.dto.BoatScheduleDto;
import com.transit.ticketing.dto.BoatScheduleResponseDto;
import com.transit.ticketing.exception.ETicketingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoatScheduleService {
    ResponseEntity<List<BoatScheduleDto>> findScheduleBoat(Long boatId) throws ETicketingException;
    ResponseEntity<List<BoatScheduleDto>> findAllScheduleBoats() throws ETicketingException;
}
