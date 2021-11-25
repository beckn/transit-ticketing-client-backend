package com.transit.ticketing.service;

import com.transit.ticketing.dto.BlockTicketRequestDto;
import com.transit.ticketing.dto.BlockTicketResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface TicketBookingServices {
    ResponseEntity<BlockTicketResponseDto> blockTicket(BlockTicketRequestDto blockTicketRequestDto);
}
