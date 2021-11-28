package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "scheduled_journey")
@Data
@NoArgsConstructor
public class ScheduledJourney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "schedule_id")
    private long scheduledId;
    @Column(name = "boat_id")
    private long boatId;
    @Column(name = "journey_date")
    private String journeyDate;
}
