package com.transit.ticketing.controller;

import com.transit.ticketing.dto.WaybillReportDto;
import com.transit.ticketing.entity.Routes;
import com.transit.ticketing.entity.Staff;
import com.transit.ticketing.entity.Trips;
import com.transit.ticketing.repository.WaybillReportRepository;
import com.transit.ticketing.service.WaybillReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        Map<String, Object> report = new HashMap<>();
        report.put("waybillReports", waybillReportRepository.findAll());
        report.put("waybillReportsCount", waybillReportRepository.count());

        return ResponseEntity.ok().body(report);
    }

}
