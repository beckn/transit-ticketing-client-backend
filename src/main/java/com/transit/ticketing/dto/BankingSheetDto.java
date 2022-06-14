package com.transit.ticketing.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class BankingSheetDto {
    private String stationCode;
    private String stationName;
    private String bankingSheetDate;
    private List<BankingSheetDataDto> sheets;
    private String totalRackAmount;
    private String totalCrAmount;
    private String totalCardAmount;
    private String netAmount;

}
