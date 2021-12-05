package com.transit.ticketing.service.impl;

import com.transit.ticketing.constants.ETicketingConstant;
import com.transit.ticketing.dto.*;
import com.transit.ticketing.entity.*;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.repository.*;
import com.transit.ticketing.service.SearchService;
import com.transit.ticketing.util.MockData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
  FareRulesRepository fareRulesRepository;
  @Autowired
  FareAttributesRepository fareAttributesRepository;
  @Value( "${app.config.availability.minutesafter}" )
  private int minutesAfter;

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
    String journeyDate = new SimpleDateFormat(ETicketingConstant.DATEFORMAT).format(new Date());
    List<TripInventory> tripInventories = inventoryRepository.findTripsForGivenSourceAndDestination(origin,destination);
    for (TripInventory tripInventory: tripInventories){
      long tripId= tripInventory.getTripId();

      StopTimes stopTimes=stopTimesRespository.findStopTimeForStopIdAndTripId(Long.parseLong(origin),tripId);
      if(stopTimes==null)continue;

      // If the departure time is within 5 mins of current time then only include in availability list

      Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ETicketingConstant.TIMEZONE));
      Date currentDate = calendar.getTime();
      TimeZone timeZone=TimeZone.getTimeZone(ETicketingConstant.TIMEZONE);
      Date dprtrTime = stopTimes.getDepartureTime();
      long diff = currentDate.getTime() - dprtrTime.getTime();
      long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
      if(minutes > minutesAfter ) continue;


      TripsInSchedule tripsInSchedule = tripInScheduleRepository.findByTripId(tripId);
      if(tripsInSchedule==null)continue;
      long scheduleId = tripsInSchedule.getScheduleId();
      ScheduledJourney scheduledJourney = scheduledJourneyRepository.fetchScheduled(scheduleId, journeyDate);
      if(scheduledJourney == null)continue;
      Boats boats = boatsRepository.findByBoat_id(scheduledJourney.getBoatId());
      if(boats==null)continue;
      int maxCapacity = boats.getCapacity();
      List<SalesRecords> salesRecords = salesRecordsRepository.issuesTicketsCount(Long.parseLong(origin),tripId,boats.getBoat_id(),scheduleId,journeyDate);
      // Get issues tickets count
      int issued = 0;
      for(SalesRecords record: salesRecords){
        issued = issued + record.getNumber_of_tickets();
      }
      int availableTickets  = maxCapacity - issued;
      FareDetailsDto fareDetailsDto = new FareDetailsDto();
      FareRules fareRules = fareRulesRepository.findByOrigin_idAndDestination_id(Long.parseLong(origin),Long.parseLong(destination));



      AvailabilityDto availabilityDto = new AvailabilityDto();
      availabilityDto.setTripId(String.valueOf(tripId));
      availabilityDto.setBoatId(String.valueOf(boats.getBoat_id()));
      if(fareRules!=null){
        FareAttributes fareAttributes = fareAttributesRepository.findByFareId(fareRules.getFare_id());
        if(fareAttributes!=null){
          fareDetailsDto.setAmount(fareAttributes.getPrice());
          fareDetailsDto.setBase(fareAttributes.getPrice());
          fareDetailsDto.setCurrency(fareAttributes.getCurrency_type());
          availabilityDto.setFareDetailsDto(fareDetailsDto);
        }
      }
      availabilityDto.setSeats(availableTickets);
      //availabilityDto.setSlot(tripsInSchedule.get);




      SimpleDateFormat localTimeFormat = new SimpleDateFormat(ETicketingConstant.TIMEFORMAT);
      localTimeFormat.setTimeZone(timeZone);
      String time = localTimeFormat.format(stopTimes.getDepartureTime());

      SimpleDateFormat sdf = new SimpleDateFormat(ETicketingConstant.DATETIMEFORMAT);
      sdf.setTimeZone(timeZone);
      String departureDateInIST = sdf.format(stopTimes.getDepartureTime());

      DepartureDto departureDto = new DepartureDto();
      departureDto.setStopId(origin);
      departureDto.setSlot(time);
      departureDto.setTimestamp(departureDateInIST);

      StopTimes stopTimesDestination=stopTimesRespository.findStopTimeForStopIdAndTripId(Long.parseLong(destination),tripId);
      if(stopTimes==null)continue;
      String timeDestination = localTimeFormat.format(stopTimesDestination.getArrivalTime());

      ArrivalDto arrivalDto = new ArrivalDto();
      arrivalDto.setStopId(destination);
      arrivalDto.setSlot(timeDestination);
      arrivalDto.setTimestamp(sdf.format(stopTimesDestination.getArrivalTime()));

      availabilityDto.setArrivalDto(arrivalDto);
      availabilityDto.setDepartureDto(departureDto);

      availabilityDtos.add(availabilityDto);

    }
    availabilityDtos.sort(new AvailabilityDTOComparator());
    TripDto tripDto = new TripDto();
    tripDto.setDate(journeyDate);
    tripDto.setSource(origin);
    tripDto.setDestination(destination);

    SearchTripDetailsDto searchTripDetailsDto = new SearchTripDetailsDto();
    searchTripDetailsDto.setTripDto(tripDto);
    searchTripDetailsDto.setAvailabilityDtos(availabilityDtos);
    return ResponseEntity.ok(searchTripDetailsDto);
  }

  private class AvailabilityDTOComparator implements Comparator<AvailabilityDto>{

    @Override
    @SneakyThrows
    public int compare(AvailabilityDto o1, AvailabilityDto o2) {
      SimpleDateFormat localTimeFormat = new SimpleDateFormat(ETicketingConstant.DATETIMEFORMAT);
      TimeZone timeZone=TimeZone.getTimeZone(ETicketingConstant.TIMEZONE);
      localTimeFormat.setTimeZone(timeZone);
      Date date1 = localTimeFormat.parse(o1.getDepartureDto().getTimestamp());
      Date date2 = localTimeFormat.parse(o2.getDepartureDto().getTimestamp());
      if(date1.before(date2))return -1;
      if(date1.after(date2))return 1;
      return 0;
    }
  }

}
