package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UPIPaymentDetailsDto {
    @JsonProperty("payment_url")
    private String paymentURL;
    @JsonProperty("reference_no")
    private String referenceNo;
}
