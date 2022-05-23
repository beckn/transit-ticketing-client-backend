package com.transit.ticketing.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoatsDto {
    private String boatNo;
    private String boatMaster;
    private String schedule;
    private String lastServiceDate;
    private String newServiceDate;
    private String status;
    private String station;
}
