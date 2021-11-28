package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="trips_in_schedule")
@Data
@NoArgsConstructor
public class TripsInSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "schedule_id")
    private long scheduleId;
    @Column(name = "trip_id")
    private long tripId;
}
