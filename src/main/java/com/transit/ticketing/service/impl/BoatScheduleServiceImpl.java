package com.transit.ticketing.service.impl;

import com.transit.ticketing.constants.ETicketingConstant;
import com.transit.ticketing.dto.BoatScheduleDto;
import com.transit.ticketing.dto.BoatScheduleResponseDto;
import com.transit.ticketing.entity.*;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.repository.*;
import com.transit.ticketing.service.BoatScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BoatScheduleServiceImpl implements BoatScheduleService {
    private static final Logger LOG = LoggerFactory.getLogger(BoatScheduleServiceImpl.class);

    @Autowired
    ScheduledJourneyRepository scheduleJourneyRepository;

    @Autowired
    BoatsRepository boatsRepository;

    @Autowired
    TripInScheduleRepository tripInScheduleRepository;

    @Autowired
    StopTimesRespository stopTimesRespository;

    @Autowired
    StopsRepository stopsRepository;

    @Override
    public ResponseEntity<Object> findScheduleBoat(Long boatId) throws ETicketingException {
        List<ScheduledJourney> scheduleList = scheduleJourneyRepository.fetchScheduleBoat(boatId);
        List<BoatScheduleDto> boatScheduleDtos = new ArrayList<>();
        for(ScheduledJourney scheduledJourney:scheduleList){
            BoatScheduleDto boatScheduleDto = new BoatScheduleDto();
            //Boat mastername can be fetched from relation to staff
            boatScheduleDto.setBoatMasterName(scheduledJourney.getBoatmaster().getStaff_name());
            long boat_id = scheduledJourney.getBoatId();
            //Boat info can be found from boat id present on schedule journey
            Boats boats = boatsRepository.findByBoat_id(boat_id);
            boatScheduleDto.setBoatNo(boats.getBoat_reg_no());
            // In order to get start time, endtime,start loc , end location we need to check stop times table
            //Get trip id from schedule id from trips in schedule table
            TripsInSchedule tripsInSchedule = tripInScheduleRepository.findByScheduleId(scheduledJourney.getScheduledId());
            long trip_id = tripsInSchedule.getTripId();
            List<StopTimes> stopTimes = stopTimesRespository.findAllByTripId(trip_id);
            StopTimes origin = stopTimes.get(0);
            StopTimes destination = stopTimes.get(stopTimes.size()-1);

            SimpleDateFormat localTimeFormat = new SimpleDateFormat(ETicketingConstant.TIMEFORMAT);
            //localTimeFormat.setTimeZone(timeZone);
            String startTime = localTimeFormat.format(origin.getDepartureTime());
            String endTime = localTimeFormat.format(destination.getArrivalTime());

            Stops originStop = stopsRepository.findById(origin.getStopId()).get();
            Stops destinationStop = stopsRepository.findById(destination.getStopId()).get();

            boatScheduleDto.setStartTime(startTime);
            boatScheduleDto.setStartLocation(originStop.getStopName());
            boatScheduleDto.setEndTime(endTime);
            boatScheduleDto.setEndLocation(destinationStop.getStopName());

            boatScheduleDtos.add(boatScheduleDto);
        }
        return ResponseEntity.ok(boatScheduleDtos);
    }

    @Override
    public ResponseEntity<Object> findAllScheduleBoats() throws ETicketingException {
        List<ScheduledJourney> scheduleList = scheduleJourneyRepository.fetchAllScheduleBoats();
        // For each schedulejourney form the response dto
        List<BoatScheduleDto> boatScheduleDtos = new ArrayList<>();
        for(ScheduledJourney scheduledJourney:scheduleList){
            BoatScheduleDto boatScheduleDto = new BoatScheduleDto();
            //Boat mastername can be fetched from relation to staff
            boatScheduleDto.setBoatMasterName(scheduledJourney.getBoatmaster().getStaff_name());
            long boat_id = scheduledJourney.getBoatId();
            //Boat info can be found from boat id present on schedule journey
            Boats boats = boatsRepository.findByBoat_id(boat_id);
            boatScheduleDto.setBoatNo(boats.getBoat_reg_no());
            // In order to get start time, endtime,start loc , end location we need to check stop times table
            //Get trip id from schedule id from trips in schedule table
            TripsInSchedule tripsInSchedule = tripInScheduleRepository.findByScheduleId(scheduledJourney.getScheduledId());
            long trip_id = tripsInSchedule.getTripId();
            List<StopTimes> stopTimes = stopTimesRespository.findAllByTripId(trip_id);
            StopTimes origin = stopTimes.get(0);
            StopTimes destination = stopTimes.get(stopTimes.size()-1);

            SimpleDateFormat localTimeFormat = new SimpleDateFormat(ETicketingConstant.TIMEFORMAT);
            //localTimeFormat.setTimeZone(timeZone);
            String startTime = localTimeFormat.format(origin.getDepartureTime());
            String endTime = localTimeFormat.format(destination.getArrivalTime());

            Stops originStop = stopsRepository.findById(origin.getStopId()).get();
            Stops destinationStop = stopsRepository.findById(destination.getStopId()).get();

            boatScheduleDto.setStartTime(startTime);
            boatScheduleDto.setStartLocation(originStop.getStopName());
            boatScheduleDto.setEndTime(endTime);
            boatScheduleDto.setEndLocation(destinationStop.getStopName());

            boatScheduleDtos.add(boatScheduleDto);
        }
        Map<String, Object> boatSchedules = new HashMap<>();
        boatSchedules.put("boatSchedules", boatScheduleDtos);
        boatSchedules.put("boatSchedulesTotalCount", boatScheduleDtos.size());

        return ResponseEntity.ok(boatSchedules);
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
