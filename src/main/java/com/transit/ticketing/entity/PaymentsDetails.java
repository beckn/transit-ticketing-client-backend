package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="payment_details")
@Data
@NoArgsConstructor
public class PaymentsDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private long order_id;
    @Column
    private String payment_link;
    @Column
    private String payment_method;
    @Column
    private String payment_status;
    @Column
    private long transaction_id;
}
