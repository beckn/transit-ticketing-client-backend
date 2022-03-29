package com.transit.ticketing.service.impl;

import com.transit.ticketing.dto.BoatScheduleResponseDto;
import com.transit.ticketing.entity.*;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.repository.BoatsRepository;
import com.transit.ticketing.repository.ScheduleRepository;
import com.transit.ticketing.repository.ScheduledJourneyRepository;
import com.transit.ticketing.service.BoatScheduleService;
import com.transit.ticketing.service.ScheduledTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoatScheduleServiceImpl implements BoatScheduleService {
    private static final Logger LOG = LoggerFactory.getLogger(BoatScheduleServiceImpl.class);

    @Autowired
    ScheduledJourneyRepository scheduleJourneyRepository;

    @Override
    public ResponseEntity<List<BoatScheduleResponseDto>> findScheduleBoat(Long boatId) throws ETicketingException {
        List<ScheduledJourney> scheduleList = scheduleJourneyRepository.fetchScheduleBoat(boatId);
        return ResponseEntity.ok(getScheduleForBoat(scheduleList));
    }

    @Override
    public ResponseEntity<List<BoatScheduleResponseDto>> findAllScheduleBoats() throws ETicketingException {
        List<ScheduledJourney> scheduleList = scheduleJourneyRepository.fetchAllScheduleBoats();
        return ResponseEntity.ok(getScheduleForBoat(scheduleList));
    }

   private List<BoatScheduleResponseDto> getScheduleForBoat(List<ScheduledJourney>  scheduleList) {
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
