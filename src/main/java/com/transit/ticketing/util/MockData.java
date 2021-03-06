package com.transit.ticketing.util;

import com.transit.ticketing.dto.AvailabilityDto;
import com.transit.ticketing.dto.SearchTripDetailsDto;
import com.transit.ticketing.dto.TripDto;
import java.util.ArrayList;
import java.util.List;

public class MockData {

  public static SearchTripDetailsDto populateSearchTripData() {
    SearchTripDetailsDto trips = new SearchTripDetailsDto();

    TripDto trip = new TripDto();
    trip.setDate("12-Nov-2021");

    List<AvailabilityDto> availabilityDtoList = new ArrayList<>();
    AvailabilityDto availability1 = new AvailabilityDto();
    availability1.setTripId("TRIP0001");
    availability1.setSeats(12);

    AvailabilityDto availability2 = new AvailabilityDto();
    availability2.setTripId("TRIP0002");
    availability2.setSeats(8);

    AvailabilityDto availability3 = new AvailabilityDto();
    availability3.setTripId("TRIP0003");
    availability3.setSeats(15);

    availabilityDtoList.add(availability1);
    availabilityDtoList.add(availability2);
    availabilityDtoList.add(availability3);

    trips.setAvailabilityDtos(availabilityDtoList);
    trips.setTripDto(trip);

    trips.setAvailabilityDtos(availabilityDtoList);
    return trips;
  }

}
