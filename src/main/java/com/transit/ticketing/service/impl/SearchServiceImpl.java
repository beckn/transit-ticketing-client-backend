package com.transit.ticketing.service.impl;

import com.transit.ticketing.dto.*;
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
import java.util.*;

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
  @Autowired
  StopsRepository stopsRepository;
  @Autowired
  StationRepository stationRepository;

  @Override
  public ResponseEntity<SearchTripDetailsDto> searchTrip(String origin, String destination,boolean isGPSBasedSearch) throws ETicketingException {
    //TODO: get stop ids from lat and long.
    // can be a post
    if(isGPSBasedSearch){
      String[] originLatLong = origin.split(",");
      String[] destinationLatLon = destination.split(",");
      Stops originStop = stopsRepository.findStopsByGPS(originLatLong[0],originLatLong[1]); //lat, lon
      if (originStop == null) throw new ETicketingException("Could not find any nearest origin stop by given gps location");
      Stops destinationStop = stopsRepository.findStopsByGPS(destinationLatLon[0],destinationLatLon[1]); //lat, lon
      if (destinationStop == null) throw new ETicketingException("Could not find any nearest destination stop by given gps location");

      origin = String.valueOf(originStop.getStop_id());
      destination = String.valueOf(destinationStop.getStop_id());
    }

    List<AvailabilityDto> availabilityDtos = new ArrayList<>();
    // Step 1: Get trips for given origin and destination for the same DOJ
    String journeyDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    List<TripInventory> tripInventories = inventoryRepository.findTripsForGivenSourceAndDestination(origin,destination);
    HashMap<Long, Station> stopIdHashMap = new HashMap<>();
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
      SimpleDateFormat timestampDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SZ");
      String timeStamp = timestampDateFormat.format(stopTimes.getArrivalTime());
      availabilityDto.setTimestamp(timeStamp);
      availabilityDto.setSlot(time);
      availabilityDtos.add(availabilityDto);
      if(!stopIdHashMap.containsKey(stopTimes.getScheduleId())){
        Station station = stationRepository.findStationById(stopTimes.getScheduleId());
        stopIdHashMap.put(stopTimes.getScheduleId(),station);
      }
    }

    TripDto tripDto = new TripDto();
    tripDto.setDate(journeyDate);
    tripDto.setSource(origin);
    tripDto.setDestination(destination);

    SearchTripDetailsDto searchTripDetailsDto = new SearchTripDetailsDto();
    searchTripDetailsDto.setTripDto(tripDto);
    searchTripDetailsDto.setAvailabilityDtos(availabilityDtos);
    List<StationResponseDto> stationResponseList = new ArrayList<>();
    for ( Map.Entry<Long,Station> stopIdMap : stopIdHashMap.entrySet()) {
      StationResponseDto stationResponseDto = new StationResponseDto();
      LocationDetailsDto location = new LocationDetailsDto();

      stationResponseDto.setStopId(stopIdMap.getKey().toString());
      stationResponseDto.setStopName(stopIdMap.getValue().getStop_name());

      location.setStopLat(stopIdMap.getValue().getStop_lat());
      location.setStopLng(stopIdMap.getValue().getStop_lon());
      stationResponseDto.setLocation(location);
      stationResponseList.add(stationResponseDto);
    }
    searchTripDetailsDto.setLocationDtos(stationResponseList);
    return ResponseEntity.ok(searchTripDetailsDto);
  }

}
