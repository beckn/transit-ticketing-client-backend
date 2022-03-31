package com.transit.ticketing.service;

import com.transit.ticketing.dto.BoatScheduleResponseDto;
import com.transit.ticketing.exception.ETicketingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CounterScheduleService {
    ResponseEntity<List<BoatScheduleResponseDto>> findCounterSchedule(long counterId) throws ETicketingException;
    ResponseEntity<List<BoatScheduleResponseDto>> findAllCountersSchedule() throws ETicketingException;
}
