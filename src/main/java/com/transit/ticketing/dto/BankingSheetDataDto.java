package com.transit.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class BankingSheetDataDto {
    private String boatNo;
    private String startingTime;
    private String waybillNumber;
    private String startingStage;
    private String endingStage;
    private String boatMaster;
    private Map<String, Integer> totalCollection;
    private String batta;
    private String warrant;
    private String Others;
    private String totalAmountDeducted;
    private String paidAmount;

}
