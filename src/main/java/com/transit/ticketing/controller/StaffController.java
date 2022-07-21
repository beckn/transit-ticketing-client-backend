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

import java.util.*;

@RestController
public class StaffController {
    private static final Logger LOG = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private StaffService staffService;

    @PostMapping(value = "/payment")
    public ResponseEntity<Object> createStaff(@RequestBody Map<String, Object> payment) {
        String amount = "0";
        Map<String, Object> message = (Map<String, Object>) payment.get("message");
        Map<String, Object> order = (Map<String, Object>) message.get("order");
        Map<String, Object> payments = (Map<String, Object>) order.get("payment");
        Map<String, Object> params = (Map<String, Object>) payments.get("params");
        amount = params.get("amount").toString();

        Map<String, String> jsonResponse = new HashMap<>();

        jsonResponse.put("clientCode", "CO1234");
        jsonResponse.put("productCode", "CMSPAY");
        jsonResponse.put("paymentDate", new Date(new Date().getTime() + (1000 * 60 * 60 * 24)).toString());
        jsonResponse.put("amount", amount);
        jsonResponse.put("bankCodeIndicator", "M-MICR");
        jsonResponse.put("beneficiaryCode", "DF345");
        jsonResponse.put("beneficiaryName", "A to Z Printing Solutions Pvt. Ltd.");
        jsonResponse.put("beneficiaryBank", "Kotak");
        jsonResponse.put("beneficiaryBranch", "Guwahati Main branch");
        jsonResponse.put("beneficiaryAccountNumber", "37476345377787");

        return ResponseEntity.ok(jsonResponse);
    }

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
        return ResponseEntity.ok().body(staffService.listAllBoatmasters());
    }

    @GetMapping(value="/api/v1/secure/staffs/ticketmaster")
    public ResponseEntity<List<StaffDto>> listAllTicketMasterStaff() {
        /*StaffDto staffDto = new StaffDto();
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

        return ResponseEntity.ok().body(staffDtos);*/
        return ResponseEntity.ok().body(staffService.listAllTicketmasters());
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
