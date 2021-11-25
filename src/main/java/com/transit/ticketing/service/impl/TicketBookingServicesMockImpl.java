package com.transit.ticketing.service.impl;

import com.transit.ticketing.dto.*;
import com.transit.ticketing.service.TicketBookingServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TicketBookingServicesMockImpl implements TicketBookingServices {
    @Override
    public ResponseEntity<BlockTicketResponseDto> blockTicket(BlockTicketRequestDto blockTicketRequestDto) {
        BlockTicketResponseDto blockTicketResponseDto = new BlockTicketResponseDto();
        blockTicketResponseDto.setTicket_no("123");
        TripDetails tripDetails = new TripDetails();
        tripDetails.setTrip_id("2000");
        tripDetails.setBoat_id("400");
        tripDetails.setDate(blockTicketRequestDto.getDate());
        tripDetails.setDestination(blockTicketRequestDto.getDestination());
        tripDetails.setSeats(blockTicketRequestDto.getSeats());
        tripDetails.setSelected_slot(blockTicketRequestDto.getSlot());
        tripDetails.setSource(blockTicketRequestDto.getSource());
        blockTicketResponseDto.setTripDetails(tripDetails);

        FareDetailsDto fareDetailsDto = new FareDetailsDto();
        fareDetailsDto.setAmount(100);
        fareDetailsDto.setBase(50);
        fareDetailsDto.setCurrency("INR");
        fareDetailsDto.setCgst(5.5);
        fareDetailsDto.setSgst(5.5);

        blockTicketResponseDto.setFareDetailsDto(fareDetailsDto);

        UPIPaymentDetailsDto upiPaymentDetailsDto = new UPIPaymentDetailsDto();
        upiPaymentDetailsDto.setPaymentURL("");

        blockTicketResponseDto.setUpiPaymentDetailsDto(upiPaymentDetailsDto);

        CardPaymentDetailsDto cardPaymentDetailsDto = new CardPaymentDetailsDto();
        cardPaymentDetailsDto.setPaymentURL("");

        blockTicketResponseDto.setCardPaymentDetailsDto(cardPaymentDetailsDto);
        return ResponseEntity.ok(blockTicketResponseDto);
    }

    @Override
    public ResponseEntity<BookTicketResponseDto> bookTicket(BookTicketRequestDto bookTicketRequestDto) {
        BookTicketResponseDto bookTicketResponseDto = new BookTicketResponseDto();
        bookTicketResponseDto.setTicketNumber(bookTicketRequestDto.getTicketNumber());
        bookTicketResponseDto.setTicketCode("testcode");
        bookTicketResponseDto.setFareDetailsDto(bookTicketRequestDto.getFareDetailsDto());
        bookTicketResponseDto.setTripDetails(bookTicketRequestDto.getTripDetails());
        CardPaymentDetailsDto cardPaymentDetailsDto = bookTicketRequestDto.getCardPaymentDetailsDto();
        cardPaymentDetailsDto.setReferenceNo("");
        UPIPaymentDetailsDto upiPaymentDetailsDto = bookTicketRequestDto.getUpiPaymentDetailsDto();
        upiPaymentDetailsDto.setReferenceNo("");
        bookTicketResponseDto.setCardPaymentDetailsDto(cardPaymentDetailsDto);
        bookTicketResponseDto.setUpiPaymentDetailsDto(upiPaymentDetailsDto);

        return ResponseEntity.ok(bookTicketResponseDto);
    }
}
