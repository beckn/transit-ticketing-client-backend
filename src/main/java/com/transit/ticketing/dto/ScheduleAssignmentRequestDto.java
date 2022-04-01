package com.transit.ticketing.dto;

import lombok.Data;

@Data
public class ScheduleAssignmentRequestDto {
    long schedule_id;
    long boat_id;
    long boat_master_id;
}
