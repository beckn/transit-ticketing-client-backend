package com.transit.ticketing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class BoatScheduleDto {
    private String boatNo;
    private String boatMasterName;
    private String startTime;
    private String endTime;
    private String startLocation;
    private String endLocation;
}
