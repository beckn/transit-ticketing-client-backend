package com.transit.ticketing.service.impl;

import com.transit.ticketing.dao.ETicketingDao;
import com.transit.ticketing.dto.*;
import com.transit.ticketing.entity.*;
import com.transit.ticketing.repository.*;
import com.transit.ticketing.service.TicketBookingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TicketBookingServicesImpl implements TicketBookingServices {
    @Autowired
    TripInScheduleRepository tripInScheduleRepository;
    @Autowired
    ScheduledJourneyRepository scheduledJourneyRepository;
    @Autowired
    BoatsRepository boatsRepository;
    @Autowired
    SalesRecordsRepository salesRecordsRepository;
    @Autowired
    FareRulesRepository fareRulesRepository;
    @Autowired
    FareAttributesRepository fareAttributesRepository;
    @Autowired
    PaymentDetailsRepository paymentDetailsRepository;

    @Override
    public ResponseEntity<BlockTicketResponseDto> blockTicket(BlockTicketRequestDto blockTicketRequestDto) {
        try {
            if(blockTicketRequestDto.getSeats()>0){
                long source = Long.parseLong(blockTicketRequestDto.getSource());
                long destination = Long.parseLong(blockTicketRequestDto.getDestination());
                long tripId = Long.parseLong(blockTicketRequestDto.getTrip_id());
                String journeyDate = blockTicketRequestDto.getDate();
                Date journeyDateInDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(journeyDate);

                TripsInSchedule tripsInSchedule = tripInScheduleRepository.findByTripId(tripId);
                long scheduleId = tripsInSchedule.getScheduleId();
                ScheduledJourney scheduledJourney = scheduledJourneyRepository.fetchScheduled(scheduleId, journeyDate);
                System.out.println(scheduledJourney);
                Boats boats = boatsRepository.findByBoat_id(scheduledJourney.getBoatId());
                System.out.println(boats);
                int maxCapacity = boats.getCapacity();
                List<SalesRecords> salesRecords = salesRecordsRepository.issuesTicketsCount(source,tripId,boats.getBoat_id(),scheduleId,journeyDate);
                System.out.println(salesRecords);
                // Get issues tickets count
                int issued = 0;
                for(SalesRecords record: salesRecords){
                    issued = issued + record.getNumber_of_tickets();
                }
                int availableTickets  = maxCapacity - issued;
                if(availableTickets >= blockTicketRequestDto.getSeats()){
                    // get fare attribute for a ticket
                    FareRules fareRules = fareRulesRepository.findByOrigin_idAndDestination_id(source,destination);
                    System.out.println(fareRules);
                    FareAttributes fareAttributes = fareAttributesRepository.findByFareId(fareRules.getFare_id());
                    System.out.println(fareAttributes);

                    long amountPaid = fareAttributes.getPrice() * blockTicketRequestDto.getSeats();

                    // Create sales record
                    SalesRecords record = new SalesRecords();
                    record.setCreated_at(new Date());
                    record.setCreated_by("Testuser");
                    record.setOrigin_jetty(source);
                    record.setDestination_jetty(destination);
                    record.setNumber_of_tickets(blockTicketRequestDto.getSeats());
                    record.setAmount_paid(amountPaid);
                    record.setTicket_fare(fareAttributes.getPrice());
                    record.setSchedule_id(scheduleId);
                    record.setBoat_id(boats.getBoat_id());
                    record.setTrip_id(tripId);
                    record.setDate_of_journey(journeyDateInDateFormat);
                    record.setStatus("Blocked");

                    SalesRecords saved = salesRecordsRepository.save(record);

                    // Return ticket details


                    BlockTicketResponseDto blockTicketResponseDto = new BlockTicketResponseDto();
                    blockTicketResponseDto.setTicket_no(String.valueOf(saved.getOrder_id()));
                    TripDetails tripDetails = new TripDetails();
                    tripDetails.setTrip_id(String.valueOf(tripId));
                    tripDetails.setBoat_id(String.valueOf(boats.getBoat_id()));
                    tripDetails.setDate(journeyDateInDateFormat.toString());
                    tripDetails.setDestination(blockTicketRequestDto.getDestination());
                    tripDetails.setSeats(blockTicketRequestDto.getSeats());
                    tripDetails.setSelected_slot(blockTicketRequestDto.getSlot());
                    tripDetails.setSource(blockTicketRequestDto.getSource());
                    blockTicketResponseDto.setTripDetails(tripDetails);

                    FareDetailsDto fareDetailsDto = new FareDetailsDto();
                    fareDetailsDto.setAmount(amountPaid);
                    fareDetailsDto.setBase(fareAttributes.getPrice());
                    fareDetailsDto.setCurrency(fareAttributes.getCurrency_type());
                /*fareDetailsDto.setCgst(5.5);
                fareDetailsDto.setSgst(5.5);*/

                    blockTicketResponseDto.setFareDetailsDto(fareDetailsDto);

                    UPIPaymentDetailsDto upiPaymentDetailsDto = new UPIPaymentDetailsDto();
                    upiPaymentDetailsDto.setPaymentURL("");

                    blockTicketResponseDto.setUpiPaymentDetailsDto(upiPaymentDetailsDto);

                    CardPaymentDetailsDto cardPaymentDetailsDto = new CardPaymentDetailsDto();
                    cardPaymentDetailsDto.setPaymentURL("");

                    blockTicketResponseDto.setCardPaymentDetailsDto(cardPaymentDetailsDto);
                    return ResponseEntity.ok(blockTicketResponseDto);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<BookTicketResponseDto> bookTicket(BookTicketRequestDto bookTicketRequestDto) {
        // Step 1 : Check if order id exists
        long orderId = Long.parseLong(bookTicketRequestDto.getTicketNumber());
        SalesRecords records=salesRecordsRepository.findSalesRecordsWithOrderId(orderId);
        if(records !=null){
            BookTicketResponseDto bookTicketResponseDto = new BookTicketResponseDto();
            bookTicketResponseDto.setTicketNumber(bookTicketRequestDto.getTicketNumber());
            bookTicketResponseDto.setTicketCode("testcode");
            bookTicketResponseDto.setFareDetailsDto(bookTicketRequestDto.getFareDetailsDto());
            bookTicketResponseDto.getFareDetailsDto().setPayment_type(bookTicketRequestDto.getPaymentType());
            bookTicketResponseDto.setTripDetails(bookTicketRequestDto.getTripDetails());

            UPIPaymentDetailsDto upiPaymentDetailsDto = bookTicketRequestDto.getUpiPaymentDetailsDto();

            CardPaymentDetailsDto cardPaymentDetailsDto = bookTicketRequestDto.getCardPaymentDetailsDto();

        // Step 2: Get payment details
            PaymentsDetails paymentsDetails = paymentDetailsRepository.findPaymentDetailsForOrderId(orderId);
            if(paymentsDetails!=null){
                if(bookTicketRequestDto.getPaymentType().equalsIgnoreCase("CARD") && paymentsDetails.getPayment_method().equalsIgnoreCase("CARD")){
                    //if(paymentsDetails==null)
                    if(paymentsDetails.getPayment_status().equalsIgnoreCase("SUCCESS")){
                        salesRecordsRepository.setStatusForSalesRecords("SUCCESS",orderId);
                        cardPaymentDetailsDto.setReferenceNo(String.valueOf(paymentsDetails.getTransaction_id()));
                    }
                }
                if(bookTicketRequestDto.getPaymentType().equalsIgnoreCase("UPI") && paymentsDetails.getPayment_method().equalsIgnoreCase("UPI")){
                    //if(paymentsDetails==null)
                    if(paymentsDetails.getPayment_status().equalsIgnoreCase("SUCCESS")){
                        salesRecordsRepository.setStatusForSalesRecords("SUCCESS",orderId);
                        upiPaymentDetailsDto.setReferenceNo(String.valueOf(paymentsDetails.getTransaction_id()));
                    }
                }
            }

            if(bookTicketRequestDto.getPaymentType().equalsIgnoreCase("CASH")){
                paymentsDetails = new PaymentsDetails();
                paymentsDetails.setPayment_link("");
                paymentsDetails.setPayment_method("CASH");
                paymentsDetails.setOrder_id(orderId);
                paymentsDetails.setTransaction_id(1111);
                paymentsDetails.setPayment_status("SUCCESS");
                paymentDetailsRepository.save(paymentsDetails);
                salesRecordsRepository.setStatusForSalesRecords("SUCCESS",orderId);
            }
            bookTicketResponseDto.setCardPaymentDetailsDto(cardPaymentDetailsDto);
            bookTicketResponseDto.setUpiPaymentDetailsDto(upiPaymentDetailsDto);
            return ResponseEntity.ok(bookTicketResponseDto);
        }

        return null;
    }
}
