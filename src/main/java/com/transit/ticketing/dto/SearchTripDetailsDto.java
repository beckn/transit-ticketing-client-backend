package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class SearchTripDetailsDto {

      @JsonProperty("trip")
      private TripDto tripDto;

      @JsonProperty("availability")
      private List<AvailabilityDto> availabilityDtos;

}
