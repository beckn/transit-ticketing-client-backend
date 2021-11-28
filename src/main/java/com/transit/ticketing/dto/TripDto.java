package com.transit.ticketing.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class TripDto {

  private String source;

  private String destination;

  private String date;

}
