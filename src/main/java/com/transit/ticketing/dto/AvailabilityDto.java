package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
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

   private String slot;

   private String seats;

}
