package com.transit.ticketing.dto;

import lombok.Data;

@Data
public class ScheduleAssignmentResponseDto {
    boolean success;
    String code;
    String message;
}
