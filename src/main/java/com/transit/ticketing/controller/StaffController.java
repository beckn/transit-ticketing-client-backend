package com.transit.ticketing.controller;

import com.transit.ticketing.dto.StationResponseDto;
import com.transit.ticketing.entity.Staff;
import com.transit.ticketing.repository.StaffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StaffController {
    private static final Logger LOG = LoggerFactory.getLogger(StaffController.class);

    private StaffRepository staffRepository;

    @PostMapping(value = "/api/v1/secure/staff")
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        if (staff == null) {
            return ResponseEntity.badRequest().body("Please send the staff details to save");
        }
        return ResponseEntity.ok(staffRepository.save(staff));
    }

    

    }
