package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class BoatScheduleResponseDto {
    @JsonProperty("boat_master_name")
    private String boatMasterName;
    @JsonProperty("boat_master_id")
    private Long boatMasterId;
    @JsonProperty("boat_reg_no")
    private String boatRegNo;
    @JsonProperty("boat_id")
    private Long boatId;
    @JsonProperty("schedule_id")
    private long schedule_id;
    @JsonProperty("journey_date")
    private String journeyDate;
    @JsonProperty("active")
    private int active;
}
