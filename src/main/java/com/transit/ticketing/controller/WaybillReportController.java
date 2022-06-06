package com.transit.ticketing.controller;

import com.transit.ticketing.dto.WayBillReportDto;
import com.transit.ticketing.repository.WaybillReportRepository;
import com.transit.ticketing.service.WaybillReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WaybillReportController {
    private static final Logger LOG = LoggerFactory.getLogger(WaybillReportController.class);

    @Autowired
    private WaybillReportService waybillReportService;

    @Autowired
    private WaybillReportRepository waybillReportRepository;

    @GetMapping(value="/api/v1/secure/generateWayBillReport")
    public ResponseEntity<Object> filterStaffBasedOnRole() {
        return ResponseEntity.ok().body(waybillReportService.updateWaybillReportData());
    }

    @GetMapping(value="/api/v1/secure/waybillreport")
    public ResponseEntity<Object> getWaybillReport() {
        LOG.info("Calling way bill service to get all the waybill reports");
        Map<String, Object> report = new HashMap<>();
//        report.put("waybillReports", waybillReportRepository.findAll());
        List<WayBillReportDto> wayBillReportDtos = waybillReportService.findAllWaybillReports();
        report.put("waybillReports", wayBillReportDtos);
        report.put("waybillReportsCount", wayBillReportDtos.size());

        return ResponseEntity.ok().body(report);
    }

}
