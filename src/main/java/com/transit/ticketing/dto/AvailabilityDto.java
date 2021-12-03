package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class AvailabilityDto {
   @JsonProperty("trip_id")
   private String tripId;
   @JsonProperty("boat_id")
   private String boatId;
   @JsonProperty("fare")
   private FareDetailsDto fareDetailsDto;
   private int seats;
   @JsonProperty("arrival")
   private ArrivalDto arrivalDto;
   @JsonProperty("departure")
   private DepartureDto departureDto;
}
