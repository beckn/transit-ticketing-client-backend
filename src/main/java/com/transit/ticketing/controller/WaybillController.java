package com.transit.ticketing.controller;

import com.transit.ticketing.dto.WaybillReportDto;
import com.transit.ticketing.entity.Staff;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WaybillController {
    @GetMapping(value="/api/v1/secure/waybillreport")
    public ResponseEntity<Object> getWaybillReport() {
        Map<String, Object> report = new HashMap<>();

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

        report.put("waybillReports", waybillReportDtos);
        report.put("waybillReportsCount", waybillReportDtos.size());

        return ResponseEntity.ok().body(report);
    }
}
