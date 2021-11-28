package com.transit.ticketing.entity;

import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="sales_records")
@Data
@NoArgsConstructor
public class SalesRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long order_id;
    @Column
    private Date created_at;
    @Column
    private String created_by;
    @Column
    private long origin_jetty;
    @Column
    private long destination_jetty;
    @Column
    private int number_of_tickets;
    @Column
    private long amount_paid;
    @Column
    private long ticket_fare;
    @Column
    private long schedule_id;
    @Column
    private long boat_id;
    @Column
    private long trip_id;
    @Column
    private Date date_of_journey;
    @Column
    private String status;
    @Column
    private String signature;
}
