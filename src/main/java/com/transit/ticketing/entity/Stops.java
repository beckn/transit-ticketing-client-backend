package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="stops")
@Data
@NoArgsConstructor
public class Stops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stop_id;
    @Column(name = "stop_name")
    private String stopName;
    @Column(name = "stop_lat")
    private String stopLat;
    @Column(name = "stop_lon")
    private String stopLon;
    @Column(name = "station_id")
    private long stationId;
}
