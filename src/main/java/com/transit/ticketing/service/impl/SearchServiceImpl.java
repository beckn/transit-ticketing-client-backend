package com.transit.ticketing.service.impl;

import com.transit.ticketing.dto.AvailabilityDto;
import com.transit.ticketing.dto.SearchTripDetailsDto;
import com.transit.ticketing.dto.TripDto;
import com.transit.ticketing.entity.*;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.repository.*;
import com.transit.ticketing.service.SearchService;
import com.transit.ticketing.util.MockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

  @Autowired
  TripInventoryRepository inventoryRepository;
  @Autowired
  TripInScheduleRepository tripInScheduleRepository;
  @Autowired
  ScheduledJourneyRepository scheduledJourneyRepository;
  @Autowired
  BoatsRepository boatsRepository;
  @Autowired
  SalesRecordsRepository salesRecordsRepository;
  @Autowired
  StopTimesRespository stopTimesRespository;

  @Override
  public ResponseEntity<SearchTripDetailsDto> searchTrip(String origin, String destination) throws ETicketingException {
    List<AvailabilityDto> availabilityDtos = new ArrayList<>();
    // Step 1: Get trips for given origin and destination for the same DOJ
    String journeyDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    List<TripInventory> tripInventories = inventoryRepository.findTripsForGivenSourceAndDestination(origin,destination);
    for (TripInventory tripInventory: tripInventories){
      long tripId= tripInventory.getTripId();

      TripsInSchedule tripsInSchedule = tripInScheduleRepository.findByTripId(tripId);
      if(tripsInSchedule==null)throw new ETicketingException("System couldn't find schedule_id for given trip_id="+tripId);
      long scheduleId = tripsInSchedule.getScheduleId();
      ScheduledJourney scheduledJourney = scheduledJourneyRepository.fetchScheduled(scheduleId, journeyDate);
      if(scheduledJourney == null)throw new ETicketingException("System couldn't find any scheduled journey for trip_id="+tripId);
      Boats boats = boatsRepository.findByBoat_id(scheduledJourney.getBoatId());
      if(boats==null)throw new ETicketingException("No boats found for given scheduled journey");
      int maxCapacity = boats.getCapacity();
      List<SalesRecords> salesRecords = salesRecordsRepository.issuesTicketsCount(Long.parseLong(origin),tripId,boats.getBoat_id(),scheduleId,journeyDate);
      // Get issues tickets count
      int issued = 0;
      for(SalesRecords record: salesRecords){
        issued = issued + record.getNumber_of_tickets();
      }
      int availableTickets  = maxCapacity - issued;
      AvailabilityDto availabilityDto = new AvailabilityDto();
      availabilityDto.setTripId(String.valueOf(tripId));
      availabilityDto.setSeats(availableTickets);
      //availabilityDto.setSlot(tripsInSchedule.get);
      StopTimes stopTimes=stopTimesRespository.findStopTimeForStopIdAndTripId(Long.parseLong(origin),tripId);
      SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
      String time = localDateFormat.format(stopTimes.getArrivalTime());
      availabilityDto.setSlot(time);
      availabilityDtos.add(availabilityDto);

    }
    TripDto tripDto = new TripDto();
    tripDto.setDate(journeyDate);
    tripDto.setSource(origin);
    tripDto.setDestination(destination);

    SearchTripDetailsDto searchTripDetailsDto = new SearchTripDetailsDto();
    searchTripDetailsDto.setTripDto(tripDto);
    searchTripDetailsDto.setAvailabilityDtos(availabilityDtos);
    return ResponseEntity.ok(searchTripDetailsDto);
  }

}
