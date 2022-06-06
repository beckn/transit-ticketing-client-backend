package com.transit.ticketing.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Entity
@Table(name = "way_bill_report")
@Data
@NoArgsConstructor
public class WayBillReport {
    @Id
    @Column(name = "way_bill_number", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wayBillNumber;

    @Column(name = "boat_number", nullable = false)
    private String boatNumber;

    @Column(name = "starting_time", nullable = false)
    private String startingTime;

    @Column(name = "starting_stage", nullable = false)
    private String startingStage;

    @Column(name = "ending_time", nullable = false)
    private String endingTime;

    @Column(name = "ending_stage", nullable = false)
    private String endingStage;

    @Column(name = "start_ticket_number", nullable = false)
    private Long startTicketNumber;

    @Column(name = "end_ticket_number", nullable = false)
    private Long endTicketNumber;

    @Column(name = "total_passengers", nullable = false)
    private Integer totalPassengers;

    @Column(name = "total_income", nullable = false)
    private Long totalIncome;

    @OneToOne
    @JoinColumn(name = "route_id" , referencedColumnName = "route_id", insertable = false, updatable = false)
    private Routes routes;

    @OneToOne
    @JoinColumn(name = "trip_id" , referencedColumnName = "trip_id", insertable = false, updatable = false)
    private Trips trips;

}