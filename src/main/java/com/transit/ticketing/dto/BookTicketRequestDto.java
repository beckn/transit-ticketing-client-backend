package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class BookTicketRequestDto {
    @JsonProperty("ticket_no")
    private String ticketNumber;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("trip")
    private TripDetails tripDetails;
    @JsonProperty("fare")
    private FareDetailsDto fareDetailsDto;
    @JsonProperty("upi_payment")
    private UPIPaymentDetailsDto upiPaymentDetailsDto;
    @JsonProperty("card_payment")
    private CardPaymentDetailsDto cardPaymentDetailsDto;
    private String signature;
}
