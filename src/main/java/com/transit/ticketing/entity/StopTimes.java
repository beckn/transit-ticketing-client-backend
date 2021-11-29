package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="trip_inventory")
@Data
@NoArgsConstructor
public class StopTimes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "stop_id")
    private long scheduleId;
    @Column(name = "trip_id")
    private long tripId;
    @Column(name="arrival_time")
    private Date arrivalTime;
    @Column(name="departure_time")
    private Date departureTime;
    @Column(name="stop_sequence")
    private int stop_sequence;
}
