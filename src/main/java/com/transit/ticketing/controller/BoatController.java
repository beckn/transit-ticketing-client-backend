package com.transit.ticketing.controller;

import com.transit.ticketing.dto.BoatsDto;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.service.BoatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BoatController {
    private static final Logger LOG = LoggerFactory.getLogger(BoatController.class);
    @Autowired
    BoatService boatService;

    @GetMapping(value = "/api/v1/secure/boats")
    public ResponseEntity<Object> search() throws ETicketingException {
        LOG.info("Received request: /api/v1/secure/boats");
        Map<String, Object> boats = new HashMap<>();

        BoatsDto boatsDto = new BoatsDto();
        boatsDto.setBoatNo("1102");
        boatsDto.setBoatMaster("Navjeet Singh");
        boatsDto.setSchedule("EKM-V Puzha");
        boatsDto.setLastServiceDate("01/11/22");
        boatsDto.setNewServiceDate("01/11/22");
        boatsDto.setStatus("New");
        boatsDto.setStation("EDATHUA");

        BoatsDto boatsDto2 = new BoatsDto();
        boatsDto2.setBoatNo("1103");
        boatsDto2.setBoatMaster("Navjeet Singh second");
        boatsDto2.setSchedule("EKM-V Puzha 2");
        boatsDto2.setLastServiceDate("01/11/22");
        boatsDto2.setNewServiceDate("01/11/22");
        boatsDto2.setStatus("Service");
        boatsDto2.setStation("EDATHUA");

        List<BoatsDto> boatsDtos = new ArrayList<>();
        boatsDtos.add(boatsDto);
        boatsDtos.add(boatsDto2);
        //return ResponseEntity.ok().body(boatService.listAllBoats());

        boats.put("boats", boatsDtos);
        boats.put("boatsCount", boatsDtos.size());
        boats.put("boatsOutOfService", boatsDtos.stream().filter(boat -> boat.getStatus().equalsIgnoreCase("Service")).count());
        boats.put("boatsAvailable", boatsDtos.stream().filter(boat -> boat.getStatus().equalsIgnoreCase("New")).count());
        return ResponseEntity.ok().body(boats);
    }

    @GetMapping(value = "/api/v1/secure/boats/page={page}&records={records}")
    public ResponseEntity<List<BoatsDto>> search(@PathVariable int page, @PathVariable int records) throws ETicketingException {
        LOG.info("Received request: /api/v1/secure/boats/page={page}&records={records}", page, records);
        //return ResponseEntity.ok().body(boatService.listAllBoats(page,records));
        BoatsDto boatsDto = new BoatsDto();
        boatsDto.setBoatNo("1102");
        boatsDto.setBoatMaster("Navjeet Singh");
        boatsDto.setSchedule("EKM-V Puzha");
        boatsDto.setLastServiceDate("01/11/22");
        boatsDto.setNewServiceDate("01/11/22");
        boatsDto.setStatus("New");
        boatsDto.setStation("EDATHUA");

        BoatsDto boatsDto2 = new BoatsDto();
        boatsDto2.setBoatNo("1103");
        boatsDto2.setBoatMaster("Navjeet Singh second");
        boatsDto2.setSchedule("EKM-V Puzha 2");
        boatsDto2.setLastServiceDate("01/11/22");
        boatsDto2.setNewServiceDate("01/11/22");
        boatsDto2.setStatus("Service");
        boatsDto2.setStation("EDATHUA");

        List<BoatsDto> boatsDtos = new ArrayList<>();
        boatsDtos.add(boatsDto);
        boatsDtos.add(boatsDto2);
        //return ResponseEntity.ok().body(boatService.listAllBoats());
        return ResponseEntity.ok().body(boatsDtos);
    }
}
