package com.transit.ticketing.controller;

import com.transit.ticketing.dto.WaybillReportDto;
import com.transit.ticketing.entity.Staff;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WaybillController {
    @GetMapping(value="/api/v1/secure/waybillreport")
    public ResponseEntity<List<WaybillReportDto>> getWaybillReport() {
        List<WaybillReportDto> waybillReportDtos = new ArrayList<>();
        WaybillReportDto waybillReportDto = new WaybillReportDto();
        waybillReportDto.setBoatMasterId(1);
        waybillReportDto.setBoatMasterName("Navjeet Singh");
        waybillReportDto.setStatus("Completed");
        waybillReportDto.setWayBillDate("01/11");
        waybillReportDto.setWayBillNumber(1);

        WaybillReportDto waybillReportDto2 = new WaybillReportDto();
        waybillReportDto2.setBoatMasterId(1);
        waybillReportDto2.setBoatMasterName("Navjeet Singh");
        waybillReportDto2.setStatus("Pending");
        waybillReportDto2.setWayBillDate("01/11");
        waybillReportDto2.setWayBillNumber(2);

        waybillReportDtos.add(waybillReportDto);
        waybillReportDtos.add(waybillReportDto2);

        return ResponseEntity.ok().body(waybillReportDtos);
    }
}
