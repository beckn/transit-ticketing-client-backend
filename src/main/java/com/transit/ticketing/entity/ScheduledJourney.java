package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

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
    @OneToOne
    @JoinColumn(name = "boatmaster", referencedColumnName = "staff_id")
    private Staff boatmaster;
    @OneToOne
    @JoinColumn(name = "boat_id" , referencedColumnName = "boat_id", insertable = false, updatable = false)
    private Boats boat;
    @OneToOne
    @JoinColumn(name = "schedule_id" , referencedColumnName = "schedule_id", insertable = false, updatable = false)
    private Schedule schedule;
}
