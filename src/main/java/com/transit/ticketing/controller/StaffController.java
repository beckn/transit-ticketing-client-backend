package com.transit.ticketing.controller;

import com.transit.ticketing.dto.StaffDto;
import com.transit.ticketing.dto.StationResponseDto;
import com.transit.ticketing.entity.Staff;
import com.transit.ticketing.repository.StaffRepository;
import com.transit.ticketing.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StaffController {
    private static final Logger LOG = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private StaffService staffService;

    @PostMapping(value = "/api/v1/secure/staff")
    public ResponseEntity<Object> createStaff(@RequestBody Staff staff) {
        if (staff == null) {
            return ResponseEntity.badRequest().body("Please send the staff details to save");
        }
        return ResponseEntity.ok(staffService.createStaff(staff));
    }

    @GetMapping(value="/api/v1/secure/staffs")
    public ResponseEntity<List<Staff>> listAllStaff() {

        return ResponseEntity.ok().body(staffService.listAllStaff());
    }

    @GetMapping(value="/api/v1/secure/staffs/boatmaster")
    public ResponseEntity<List<StaffDto>> listAllBoatMasterStaff() {
        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId("1");
        staffDto.setStaffName("Navjeet Singh");
        staffDto.setBoatNumber("1102");
        staffDto.setDateOfJoining("01-11-22");
        staffDto.setStation("EDATHUA");
        staffDto.setPosition("Senior");
        staffDto.setShift("Morning");
        staffDto.setRole("Boatmaster");

        StaffDto staffDto2 = new StaffDto();
        staffDto2.setStaffId("2");
        staffDto2.setStaffName("Navjeet Singh");
        staffDto2.setBoatNumber("1102");
        staffDto2.setDateOfJoining("01-11-22");
        staffDto2.setStation("EDATHUA");
        staffDto2.setPosition("Senior");
        staffDto2.setShift("Morning");
        staffDto2.setRole("Boatmaster");

        List<StaffDto> staffDtos = new ArrayList<>();
        staffDtos.add(staffDto);
        staffDtos.add(staffDto2);

        return ResponseEntity.ok().body(staffDtos);
    }

    @GetMapping(value="/api/v1/secure/staffs/ticketmaster")
    public ResponseEntity<List<StaffDto>> listAllTicketMasterStaff() {
        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId("3");
        staffDto.setStaffName("Navjeet Singh");
        staffDto.setBoatNumber("1102");
        staffDto.setDateOfJoining("01-11-22");
        staffDto.setStation("EDATHUA");
        staffDto.setPosition("Senior");
        staffDto.setShift("Morning");
        staffDto.setRole("Ticketmaster");

        StaffDto staffDto2 = new StaffDto();
        staffDto2.setStaffId("4");
        staffDto2.setStaffName("Navjeet Singh");
        staffDto2.setBoatNumber("1102");
        staffDto2.setDateOfJoining("01-11-22");
        staffDto2.setStation("EDATHUA");
        staffDto2.setPosition("Senior");
        staffDto2.setShift("Morning");
        staffDto2.setRole("Ticketmaster");

        List<StaffDto> staffDtos = new ArrayList<>();
        staffDtos.add(staffDto);
        staffDtos.add(staffDto2);

        return ResponseEntity.ok().body(staffDtos);
    }

    @GetMapping(value="/api/v1/secure/staffs?page={page}&records={records}")
    public ResponseEntity<Page<Staff>> listAllStaffWithPagination(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "20") int records) {
        LOG.info(String.valueOf(page));
        return ResponseEntity.ok().body(staffService.listAllStaffWithPagination(page, records));
    }

    @GetMapping(value="/api/v1/secure/staff/role={role}")
    public ResponseEntity<List<Staff>> filterStaffBasedOnRole(@PathVariable String role) {
        return ResponseEntity.ok().body(staffService.filterStaffsByRole(role));
    }

}
