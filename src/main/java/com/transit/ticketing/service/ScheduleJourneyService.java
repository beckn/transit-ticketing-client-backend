package com.transit.ticketing.service;

import com.transit.ticketing.dto.ScheduleAssignmentRequestDto;
import com.transit.ticketing.dto.ScheduleAssignmentResponseDto;
import com.transit.ticketing.entity.Boats;
import com.transit.ticketing.entity.Schedule;
import com.transit.ticketing.entity.ScheduledJourney;
import com.transit.ticketing.entity.Staff;
import com.transit.ticketing.repository.BoatsRepository;
import com.transit.ticketing.repository.ScheduleRepository;
import com.transit.ticketing.repository.ScheduledJourneyRepository;
import com.transit.ticketing.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleJourneyService {
    @Autowired
    ScheduledJourneyRepository scheduledJourneyRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    BoatsRepository boatsRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    public void assignBoatMasterAndBoatToScheduleJourney(Staff staff_id, long boat_id, long schedule_id){
        ScheduledJourney scheduledJourney = scheduledJourneyRepository.fetchScheduled(schedule_id,null);
        scheduledJourney.setBoatId(boat_id);
        scheduledJourney.setBoatmaster(staff_id);
        scheduledJourneyRepository.save(scheduledJourney);
    }

    public ScheduleAssignmentResponseDto saveAssignment(ScheduleAssignmentRequestDto assignmentRequestDto){
        ScheduleAssignmentResponseDto scheduleAssignmentResponseDto = new ScheduleAssignmentResponseDto();
        // Step 1- Validate if staff present;
        Optional<Staff> staff = staffRepository.findById(assignmentRequestDto.getBoat_master_id());
        if(staff.isEmpty()){
            scheduleAssignmentResponseDto.setSuccess(false);
            scheduleAssignmentResponseDto.setCode("ERR01");
            scheduleAssignmentResponseDto.setMessage("Staff not found");
            return scheduleAssignmentResponseDto;
        }
        // Step 2- Validate if boat present
        Optional<Boats> boats = boatsRepository.findById(assignmentRequestDto.getBoat_id());
        if(boats.isEmpty()){
            scheduleAssignmentResponseDto.setSuccess(false);
            scheduleAssignmentResponseDto.setCode("ERR02");
            scheduleAssignmentResponseDto.setMessage("Boat not found");
            return scheduleAssignmentResponseDto;
        }
        // Step 3- Validate if schedule present
        Optional<Schedule> schedule = scheduleRepository.findById(assignmentRequestDto.getSchedule_id());
        if(schedule.isEmpty()){
            scheduleAssignmentResponseDto.setSuccess(false);
            scheduleAssignmentResponseDto.setCode("ERR03");
            scheduleAssignmentResponseDto.setMessage("Schedule not found");
            return scheduleAssignmentResponseDto;
        }
        try{
            assignBoatMasterAndBoatToScheduleJourney(staff.get(),boats.get().getBoat_id(),schedule.get().getSchedule_id());
        }catch (Exception ex){
            scheduleAssignmentResponseDto.setSuccess(false);
            scheduleAssignmentResponseDto.setCode("ERR04");
            scheduleAssignmentResponseDto.setMessage("Error occured while saving assignment");
            return scheduleAssignmentResponseDto;
        }
        scheduleAssignmentResponseDto.setSuccess(true);
        scheduleAssignmentResponseDto.setCode("");
        scheduleAssignmentResponseDto.setMessage("Successfully saved assignment");
        return scheduleAssignmentResponseDto;

    }
}
