package com.transit.ticketing.dto;

import com.transit.ticketing.entity.Routes;
import com.transit.ticketing.entity.Trips;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class WayBillReportDto {
    private long wayBillNumber;
    private long boatMasterId;
    private String boatNumber;
//    private String wayBillDate;
    private String boatMasterName;
    private String status;
    private String startingTime;
    private String startingStage;
    private String endingTime;
    private String endingStage;
    private Long startTicketNumber;
    private Long endTicketNumber;
    private Integer totalPassengers;
    private Long totalIncome;
    private Routes routes;
    private List<Trips> trips;

}
