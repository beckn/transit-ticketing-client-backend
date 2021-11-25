package com.transit.ticketing.controller;

import com.transit.ticketing.dto.BlockTicketRequestDto;
import com.transit.ticketing.dto.BlockTicketResponseDto;
import com.transit.ticketing.service.TicketBookingServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ETicketingController {
    private static final Logger LOG = LoggerFactory.getLogger(ETicketingController.class);

    @Autowired
    TicketBookingServices ticketBookingServices;

    @PostMapping("/block_ticket")
    public ResponseEntity<BlockTicketResponseDto> blockTicket(@RequestBody BlockTicketRequestDto blockTicketRequestDto){
        LOG.info("Received request to block ticket");
        return ticketBookingServices.blockTicket(blockTicketRequestDto);
    }

}
