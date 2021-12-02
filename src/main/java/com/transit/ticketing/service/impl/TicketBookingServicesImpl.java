package com.transit.ticketing.service.impl;

import antlr.StringUtils;
import com.transit.ticketing.cipher.AESUtil;
import com.transit.ticketing.constants.ETicketingConstant;
import com.transit.ticketing.dto.*;
import com.transit.ticketing.entity.*;
import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.repository.*;
import com.transit.ticketing.service.TicketBookingServices;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    @Value( "${app.security.key}" )
    private String key;

    @Value( "${app.security.iv}" )
    private String iv;

    @Override
    @Transactional
    public ResponseEntity<BlockTicketResponseDto> blockTicket(BlockTicketRequestDto blockTicketRequestDto) throws ETicketingException {
        try {
            // Validation
            if(blockTicketRequestDto.getSeats()<=0) throw new ETicketingException("Please provide seat number greater than 0 to proceed");
            // Validation
            validateMandFields(blockTicketRequestDto);
            long source = Long.parseLong(blockTicketRequestDto.getSource());
            long destination = Long.parseLong(blockTicketRequestDto.getDestination());
            long tripId = Long.parseLong(blockTicketRequestDto.getTrip_id());
            String journeyDate = blockTicketRequestDto.getDate();
            Date journeyDateInDateFormat = new SimpleDateFormat(ETicketingConstant.DATEFORMAT).parse(journeyDate);
            TripsInSchedule tripsInSchedule = tripInScheduleRepository.findByTripId(tripId);
            if(tripsInSchedule==null)throw new ETicketingException("System couldn't find schedule_id for given trip_id="+tripId);
            long scheduleId = tripsInSchedule.getScheduleId();
            ScheduledJourney scheduledJourney = scheduledJourneyRepository.fetchScheduled(scheduleId, journeyDate);
            if(scheduledJourney == null)throw new ETicketingException("System couldn't find any scheduled journey for trip_id="+tripId);
            Boats boats = boatsRepository.findByBoat_id(scheduledJourney.getBoatId());
            if(boats==null)throw new ETicketingException("No boats found for given scheduled journey");
            int maxCapacity = boats.getCapacity();
            List<SalesRecords> salesRecords = salesRecordsRepository.issuesTicketsCount(source,tripId,boats.getBoat_id(),scheduleId,journeyDate);
            // Get issues tickets count
            int issued = 0;
            for(SalesRecords record: salesRecords){
                issued = issued + record.getNumber_of_tickets();
            }
            int availableTickets  = maxCapacity - issued;
            if(availableTickets < blockTicketRequestDto.getSeats()) throw new ETicketingException("Less tickets are available. Wont proceed with blocking tickets");
            // get fare attribute for a ticket
            FareRules fareRules = fareRulesRepository.findByOrigin_idAndDestination_id(source,destination);
            FareAttributes fareAttributes = fareAttributesRepository.findByFareId(fareRules.getFare_id());

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
            record.setStatus(ETicketingConstant.BLOCKED);

            SalesRecords saved = salesRecordsRepository.save(record);
            //Update signature in sales record
            String signature = "order_id:"+saved.getOrder_id()+";trip_id:"+tripId+";schedule_id:"+scheduleId+";doj:"+journeyDate+";ori_stop:"+source+";dest_stop:"+destination+";no:"+ blockTicketRequestDto.getSeats()+";created:"+new Date();
            System.out.println(signature);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            String encryptedSign = AESUtil.encrypt(signature,skeySpec,AESUtil.generateIv(iv));
            salesRecordsRepository.setSignatureForSalesRecords(encryptedSign,saved.getOrder_id());
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
        } catch (ParseException | ETicketingException | UnsupportedEncodingException e) {
            throw new ETicketingException(e);
        } catch (NumberFormatException nfe ){
            throw new ETicketingException("Please provide valid input for blocking tickets");
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new ETicketingException("Error occured while encrypting sign");
        } finally {

        }
    }

    private void validateMandFields(BlockTicketRequestDto blockTicketRequestDto) throws ETicketingException {
        if(Strings.isEmpty(blockTicketRequestDto.getTrip_id()) || Strings.isEmpty(blockTicketRequestDto.getDestination()) || Strings.isEmpty(blockTicketRequestDto.getSource()) || Strings.isEmpty(blockTicketRequestDto.getDate()))throw new ETicketingException("Please provide mandatory fields to block tickets");
    }

    @Override
    @Transactional
    public ResponseEntity<BookTicketResponseDto> bookTicket(BookTicketRequestDto bookTicketRequestDto) throws ETicketingException {
        // Step 1 : Check if order id exists
        long orderId = Long.parseLong(bookTicketRequestDto.getTicketNumber());
        SalesRecords records=salesRecordsRepository.findSalesRecordsWithOrderId(orderId);
        if(records==null)throw new ETicketingException("No sales record found for given order id="+orderId);

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
        if(bookTicketRequestDto.getPaymentType().equalsIgnoreCase("CASH")){
            paymentsDetails = new PaymentsDetails();
            paymentsDetails.setPayment_link("");
            paymentsDetails.setPayment_method("CASH");
            paymentsDetails.setOrder_id(orderId);
            paymentsDetails.setTransaction_id(1111);
            paymentsDetails.setPayment_status("SUCCESS");
            paymentDetailsRepository.save(paymentsDetails);
            salesRecordsRepository.setStatusForSalesRecords(ETicketingConstant.SUCCESS,orderId);
            bookTicketResponseDto.setCardPaymentDetailsDto(cardPaymentDetailsDto);
            bookTicketResponseDto.setUpiPaymentDetailsDto(upiPaymentDetailsDto);
            return ResponseEntity.ok(bookTicketResponseDto);
        }
        if (paymentsDetails == null) throw new ETicketingException("No Payment details found.");

        if(bookTicketRequestDto.getPaymentType().equalsIgnoreCase("CARD") && paymentsDetails.getPayment_method().equalsIgnoreCase("CARD")){
            //if(paymentsDetails==null)
            if(paymentsDetails.getPayment_status().equalsIgnoreCase("SUCCESS")){
                salesRecordsRepository.setStatusForSalesRecords(ETicketingConstant.SUCCESS,orderId);
                cardPaymentDetailsDto.setReferenceNo(String.valueOf(paymentsDetails.getTransaction_id()));
            }
        }else {
            throw new ETicketingException("Given payment mode doesn't match with payment details records.");
        }
        if(bookTicketRequestDto.getPaymentType().equalsIgnoreCase("UPI") && paymentsDetails.getPayment_method().equalsIgnoreCase("UPI")){
            //if(paymentsDetails==null)
            if(paymentsDetails.getPayment_status().equalsIgnoreCase("SUCCESS")){
                salesRecordsRepository.setStatusForSalesRecords(ETicketingConstant.SUCCESS,orderId);
                upiPaymentDetailsDto.setReferenceNo(String.valueOf(paymentsDetails.getTransaction_id()));
            }
        }else {
            throw new ETicketingException("Given payment mode doesn't match with payment details records.");
        }


        bookTicketResponseDto.setCardPaymentDetailsDto(cardPaymentDetailsDto);
        bookTicketResponseDto.setUpiPaymentDetailsDto(upiPaymentDetailsDto);
        return ResponseEntity.ok(bookTicketResponseDto);

    }
}
