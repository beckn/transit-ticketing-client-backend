package com.transit.ticketing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UPIPaymentDetailsDto {
    private String paymentURL;
}
