package com.transit.ticketing.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StaffDto {
    private String staffId;
    private String staffName;
    private String boatNumber;
    private String role;
    private String dateOfJoining;
    private String station;
    private String position;
    private String shift;
}
