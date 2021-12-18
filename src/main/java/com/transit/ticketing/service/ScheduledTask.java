package com.transit.ticketing.service;

import com.transit.ticketing.controller.ETicketingController;
import com.transit.ticketing.entity.*;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduledTask {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTask.class);
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    TripInScheduleRepository tripInScheduleRepository;
    @Autowired
    TripsRepository tripsRepository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    StopTimesRespository stopTimesRespository;
    @Autowired
    TripInventoryRepository inventoryRepository;

    public void createJourney() throws ETicketingException {
        LOG.info("ScheduledTask started to create trip inventory entries");
        /*
            Pseudocode for con job :

            Get all schedules from schedule
            For each schedule do following
                Get schedule id, start date and end date
                Get trip id using schedule id from trips_in_schedule
                For date in start date to end date do following
                    Get service id using trip id from trips
                    Get calender using service id from Calender
                    If day in date has value 1 in calender for that day  do following
                        Get stop times using trip id from stop times in sequence asc order
                        For each stop times
                        Create list of trip inventory with date, stop id , trip id, stop seq, issuedTickets=0
            Save list of inventories
         */
        List<TripInventory> tripInventories = new ArrayList<>();
        List<Schedule> scheduleList = scheduleRepository.findAllActiveSchedules();
        for(Schedule schedule: scheduleList){
            LOG.info("Found active schedule to create new entries");
            TripsInSchedule tripsInSchedule = tripInScheduleRepository.findByScheduleId(schedule.getSchedule_id());
            for(Date date = schedule.getStart_date();!date.after(schedule.getEnd_date());date = getDate(date)){
                if(tripsInSchedule==null) throw new ETicketingException("No trips in schedule found");
                Optional<Trips> trips = tripsRepository.findById(tripsInSchedule.getTripId());
                Trips trips1 = trips.get();
                Optional<ServiceCalendar> serviceCalendar = calendarRepository.findById(trips1.getService_id());
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); // Days of week are indexed starting at 1 for Sunday, see Calendar.SUNDAY
                LOG.info("Day of the week in date "+date+" is :"+dayOfWeek);
                boolean isScheduledForThisDay = checkScheduleByCalendar(serviceCalendar, dayOfWeek);;
                if (isScheduledForThisDay){
                    LOG.info("Creating records for trip id :"+trips1.getTrip_id() +" for date:"+date);
                    List<StopTimes> stopTimes = stopTimesRespository.findAllByTripId(trips1.getTrip_id());

                    for(StopTimes times: stopTimes){
                        TripInventory tripInventory = new TripInventory();
                        tripInventory.setTripId(trips1.getTrip_id());
                        tripInventory.setDoj(date);
                        tripInventory.setStopId(times.getStopId());
                        tripInventory.setIssuedTickets(0);
                        tripInventory.setStopSequence(times.getStop_sequence());
                        tripInventories.add(tripInventory);
                    }
                }else {
                    LOG.info("No matched day found on Calendar table. Check in calendar table and update day as required.");
                }
            }
            schedule.setActive(0);
            scheduleRepository.save(schedule);
        }
        LOG.info("Saving records into Trip Invetory");
        if(tripInventories.size()>0)inventoryRepository.saveAll(tripInventories);
        LOG.info("ScheduledTask completed successfully.");
    }

    private boolean checkScheduleByCalendar(Optional<ServiceCalendar> serviceCalendar, int dayOfWeek) {
        boolean isScheduledForThisDay = false;
        switch (dayOfWeek){
            case 1: {
                if(serviceCalendar.get().getSunday()==1)isScheduledForThisDay=true;
                break;
            }
            case 2: {
                if(serviceCalendar.get().getMonday()==1)isScheduledForThisDay=true;
                break;
            }
            case 3: {
                if(serviceCalendar.get().getTuesday()==1)isScheduledForThisDay=true;
                break;
            }
            case 4: {
                if(serviceCalendar.get().getWednesday()==1)isScheduledForThisDay=true;
                break;
            }
            case 5: {
                if(serviceCalendar.get().getThursday()==1)isScheduledForThisDay=true;
                break;
            }
            case 6: {
                if(serviceCalendar.get().getFriday()==1)isScheduledForThisDay=true;
                break;
            }
            case 7: {
                if(serviceCalendar.get().getSaturday()==1)isScheduledForThisDay=true;
                break;
            }
        }
        return isScheduledForThisDay;
    }

    private Date getDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return date;
    }
}
