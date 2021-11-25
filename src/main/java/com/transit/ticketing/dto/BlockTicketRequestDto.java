package com.transit.ticketing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class BlockTicketRequestDto {
    private String source;
    private String destination;
    private String date;
    private String slot;
    private int seats;
    private String trip_id;
}
