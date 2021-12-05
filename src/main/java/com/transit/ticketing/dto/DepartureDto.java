package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DepartureDto {
    @JsonProperty("stop_id")
    private String stopId;
    private String slot;
    private String timestamp;
}
