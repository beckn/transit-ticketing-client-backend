package com.transit.ticketing.service;

import com.transit.ticketing.entity.Staff;
import com.transit.ticketing.repository.StaffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

@Service
public class StaffService {
    private static final Logger LOG = LoggerFactory.getLogger(StaffService.class);


    @Autowired
    private StaffRepository staffRepository;

//    Create initial data for testing
//    @PostConstruct
//    public void initDB() {
//        List<Staff> staffList = IntStream.rangeClosed(1,200)
//                .mapToObj(i -> new Staff(i, "staff"+i, new Random().nextInt(100), "BoatMaster"))
//                .collect(Collectors.toList());
//        staffRepository.saveAll(staffList);
//
//        staffList = IntStream.rangeClosed(201,400)
//                .mapToObj(i -> new Staff(i, "staff"+i, new Random().nextInt(200), "TicketMaster"))
//                .collect(Collectors.toList());
//        staffRepository.saveAll(staffList);
//    }

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Page<Staff> listAllStaff(int pageNo, int numberOfRecords) {
        return staffRepository.findAll(
                PageRequest.of(pageNo, numberOfRecords));

    }

    public List<Staff> filterStaffsByRole(String role) {
        return staffRepository.findByRole(role);
    }

}
