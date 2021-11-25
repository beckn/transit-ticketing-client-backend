package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class BlockTicketResponseDto {
    private String ticket_no;
    @JsonProperty("trip")
    private TripDetails tripDetails;
    @JsonProperty("fare")
    private FareDetailsDto fareDetailsDto;
    @JsonProperty("upi_payment")
    private UPIPaymentDetailsDto upiPaymentDetailsDto;
    @JsonProperty("card_payment")
    private CardPaymentDetailsDto cardPaymentDetailsDto;
}
