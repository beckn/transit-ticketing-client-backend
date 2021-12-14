package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TripDetails {
    private String source;
    private String destination;
    private String date;
    private String selected_slot;
    private int seats;
    private String trip_id;
    private String boat_id;
    @JsonProperty("arrival")
    private ArrivalDto arrivalDto;
    @JsonProperty("departure")
    private DepartureDto departureDto;
}
