package com.transit.ticketing.service.impl;

import com.transit.ticketing.dto.BoatScheduleResponseDto;
import com.transit.ticketing.entity.ScheduledJourney;
import com.transit.ticketing.entity.Stops;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.repository.StopsRepository;
import com.transit.ticketing.service.CounterScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CounterScheduleServiceImpl implements CounterScheduleService {
    private static final Logger LOG = LoggerFactory.getLogger(CounterScheduleServiceImpl.class);
    @Autowired
    StopsRepository stopsRepository;

    @Override
    public ResponseEntity<List<BoatScheduleResponseDto>> findCounterSchedule(long counterId) throws ETicketingException {
        List<Stops> scheduleList = stopsRepository.findStopsById(counterId);
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Override
    public ResponseEntity<List<BoatScheduleResponseDto>> findAllCountersSchedule() throws ETicketingException {
        List<Stops> scheduleList = stopsRepository.findAllStops();
        return ResponseEntity.ok(new ArrayList<>());
    }

    private List<BoatScheduleResponseDto> getScheduleForCounters(List<ScheduledJourney>  scheduleList) {
        List<BoatScheduleResponseDto> boatScheduleResponseDtoList = new ArrayList();
        for(ScheduledJourney schedule: scheduleList){
            LOG.info("Found all schedules for boats");
            BoatScheduleResponseDto boatScheduleResponseDto = new BoatScheduleResponseDto();
            boatScheduleResponseDto.setBoatId(schedule.getBoatId());
            boatScheduleResponseDto.setSchedule_id(schedule.getScheduledId());
            boatScheduleResponseDto.setActive(schedule.getSchedule() != null ? schedule.getSchedule().getActive() : null);
            boatScheduleResponseDto.setBoatId(schedule.getBoat() != null ? schedule.getBoat().getBoat_id() : null);
            boatScheduleResponseDto.setBoatRegNo(schedule.getBoat() != null ? schedule.getBoat().getBoat_reg_no() : null);
            boatScheduleResponseDto.setBoatMasterId(schedule.getBoatmaster() != null ? schedule.getBoatmaster().getStaff_id() : null);
            boatScheduleResponseDto.setBoatMasterName(schedule.getBoatmaster() != null ? schedule.getBoatmaster().getStaff_name() : null);
            boatScheduleResponseDto.setJourneyDate(schedule.getJourneyDate());
            boatScheduleResponseDtoList.add(boatScheduleResponseDto);
        }
        LOG.info("End of finding all schedules for boats");
        return boatScheduleResponseDtoList;
    }
}