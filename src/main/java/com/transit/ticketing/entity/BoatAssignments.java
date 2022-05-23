package com.transit.ticketing.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="boat_assignments")
@Data
@NoArgsConstructor
public class BoatAssignments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "boats_boat_id")
    Boats boats;

    @ManyToOne
    @JoinColumn(name = "stations_station_id")
    Stations stations;

    @ManyToOne
    @JoinColumn(name = "schedule_schedule_id")
    Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "staff_staff_id")
    Staff staff;

}
