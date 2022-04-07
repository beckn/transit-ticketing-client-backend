package com.transit.ticketing.service;

import com.transit.ticketing.dto.BoatsDto;
import com.transit.ticketing.entity.Boats;
import com.transit.ticketing.repository.BoatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoatService {
    @Autowired
    BoatsRepository boatsRepository;
    public List<BoatsDto> listAllBoats() {
        List<Boats> boats = boatsRepository.findAll();
        List<BoatsDto> boatsDtos = new ArrayList<>();
        for(Boats boat: boats){
            BoatsDto boatsDto = new BoatsDto();
            boatsDto.setBootNo(boat.getBoat_reg_no());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            boatsDto.setLastServiceDate(df.format(boat.getLast_service_dt()));
            boatsDto.setNewServiceDate(df.format(boat.getNew_service_dt()));
            boatsDto.setStatus(boat.getRemarks());
            boatsDto.setStation(boat.getStations().getStation_name());
            boatsDto.setBoatMaster(boatsRepository.findBoatMasterByBoat_id(boat.getBoat_id()));
            boatsDto.setSchedule(boatsRepository.findScheduleNameByBoat_id(boat.getBoat_id()));
            boatsDtos.add(boatsDto);
        }
        return boatsDtos;
    }
    public List<BoatsDto> listAllBoats(int pageNo,int numberOfRecords) {
        Page<Boats> boats = boatsRepository.findAll(PageRequest.of(pageNo, numberOfRecords));
        List<Boats> boatsList = boats.getContent();
        List<BoatsDto> boatsDtos = new ArrayList<>();
        for(Boats boat: boats){
            BoatsDto boatsDto = new BoatsDto();
            boatsDto.setBootNo(boat.getBoat_reg_no());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            boatsDto.setLastServiceDate(df.format(boat.getLast_service_dt()));
            boatsDto.setNewServiceDate(df.format(boat.getNew_service_dt()));
            boatsDto.setStatus(boat.getRemarks());
            boatsDto.setStation(boat.getStations().getStation_name());
            boatsDto.setBoatMaster(boatsRepository.findBoatMasterByBoat_id(boat.getBoat_id()));
            boatsDto.setSchedule(boatsRepository.findScheduleNameByBoat_id(boat.getBoat_id()));
            boatsDtos.add(boatsDto);
        }
        return boatsDtos;
    }
}
