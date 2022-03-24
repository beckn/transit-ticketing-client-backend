package com.transit.ticketing.service;

import com.transit.ticketing.entity.Staff;
import com.transit.ticketing.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Page<Staff> listAllStaff(int pageNo, int numberOfRecords) {
        return staffRepository.findAll(
                PageRequest.of(pageNo, numberOfRecords).withSort(Sort.by(Sort.Direction.ASC, "staff_name")));
    }

    public List<Staff> filterStaffsByRole(String role) {
        return staffRepository.findByRole(role);
    }

}
