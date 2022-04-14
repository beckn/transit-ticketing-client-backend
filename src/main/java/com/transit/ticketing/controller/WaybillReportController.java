package com.transit.ticketing.controller;

import com.transit.ticketing.entity.Staff;
import com.transit.ticketing.service.WaybillReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WaybillReportController {
    private static final Logger LOG = LoggerFactory.getLogger(WaybillReportController.class);

    @Autowired
    private WaybillReportService waybillReportService;

    @GetMapping(value="/api/v1/secure/generateWayBillReport")
    public ResponseEntity<Object> filterStaffBasedOnRole() {
        return ResponseEntity.ok().body(waybillReportService.updateWaybillReportData());
    }

}
