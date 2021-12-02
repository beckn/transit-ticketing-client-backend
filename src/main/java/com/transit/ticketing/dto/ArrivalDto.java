package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ArrivalDto {
    @JsonProperty("stop_id")
    private String stopId;
    private String slot;
    private String timestamp;
    private final String label="Arrival";
}
