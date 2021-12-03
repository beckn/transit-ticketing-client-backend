package com.transit.ticketing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FareDetailsDto {
    private double amount;
    private String currency;
    private double base;
    private double cgst;
    private double sgst;
    private String payment_type;
}
