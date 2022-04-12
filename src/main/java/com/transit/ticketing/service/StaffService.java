package com.transit.ticketing.service;

import com.transit.ticketing.dto.StaffDto;
import com.transit.ticketing.entity.Boats;
import com.transit.ticketing.entity.Staff;
import com.transit.ticketing.repository.BoatsRepository;
import com.transit.ticketing.repository.ScheduledJourneyRepository;
import com.transit.ticketing.repository.StaffRepository;
import com.transit.ticketing.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

@Service
public class StaffService {
    private static final Logger LOG = LoggerFactory.getLogger(StaffService.class);


    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private BoatsRepository boatsRepository;


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

    public List<Staff> listAllStaff() {
        return staffRepository.findAll();

    }

    public List<Staff> filterStaffsByRole(String role) {
        return staffRepository.findByRole(role);
    }

    public Page<Staff> listAllStaffWithPagination(int pageNo, int numberOfRecords) {
        return staffRepository.findAll(
                PageRequest.of(pageNo, numberOfRecords));
    }

    public List<StaffDto> listAllBoatmasters(){
        List<Staff> staffList = staffRepository.findAllBoatMasters();
        List<StaffDto> staffDtos = new ArrayList<>();
        for(Staff staff: staffList){
            StaffDto staffDto = new StaffDto();
            staffDto.setStaffId(String.valueOf(staff.getStaff_id()));
            staffDto.setStaffName(staff.getStaff_name());
            staffDto.setDateOfJoining(staff.getDoj());
            staffDto.setPosition(staff.getPosition());
            staffDto.setShift(staff.getShift());
            staffDto.setStation(staff.getStations().getStation_name());
            staffDto.setRole(staff.getRole());
            // Get boat number
            Boats boats = boatsRepository.findByStationId(staff.getStation_id());
            staffDto.setBoatNumber(boats.getBoat_reg_no());
            staffDtos.add(staffDto);
        }
        return staffDtos;

    }

    public List<StaffDto> listAllTicketmasters(){
        List<Staff> staffList = staffRepository.findAllTicketMasters();
        List<StaffDto> staffDtos = new ArrayList<>();
        for(Staff staff: staffList){
            StaffDto staffDto = new StaffDto();
            staffDto.setStaffId(String.valueOf(staff.getStaff_id()));
            staffDto.setStaffName(staff.getStaff_name());
            staffDto.setDateOfJoining(staff.getDoj());
            staffDto.setPosition(staff.getPosition());
            staffDto.setShift(staff.getShift());
            staffDto.setStation(staff.getStations().getStation_name());
            staffDto.setRole(staff.getRole());
            // Get boat number
            Boats boats = boatsRepository.findByStationId(staff.getStation_id());
            staffDto.setBoatNumber(boats.getBoat_reg_no());
            staffDtos.add(staffDto);
        }
        return staffDtos;

    }
}
