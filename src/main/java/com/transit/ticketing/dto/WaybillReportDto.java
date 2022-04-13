package com.transit.ticketing.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class WaybillReportDto {
    private long wayBillNumber;
    private long boatMasterId;
    private long boatNumber;
    private String wayBillDate;
    private String boatMasterName;
    private String status;
}
