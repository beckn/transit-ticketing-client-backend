package com.transit.ticketing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class FareDetailsDto {
    private double amount;
    private String currency;
    private double base;
    private double cgst;
    private double sgst;
}
