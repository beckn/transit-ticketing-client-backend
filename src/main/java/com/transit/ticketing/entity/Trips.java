package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="trips")
@Data
@NoArgsConstructor
public class Trips {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trip_id;
    @Column
    private long route_id;
    @Column
    private long service_id;
    @Column
    private String trip_headsign;
}
