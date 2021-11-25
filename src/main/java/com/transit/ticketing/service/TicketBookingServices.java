package com.transit.ticketing.service;

import com.transit.ticketing.dto.BlockTicketRequestDto;
import com.transit.ticketing.dto.BlockTicketResponseDto;
import com.transit.ticketing.dto.BookTicketRequestDto;
import com.transit.ticketing.dto.BookTicketResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface TicketBookingServices {
    ResponseEntity<BlockTicketResponseDto> blockTicket(BlockTicketRequestDto blockTicketRequestDto);
    ResponseEntity<BookTicketResponseDto> bookTicket(BookTicketRequestDto bookTicketRequestDto);
}
