package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="stations")
@Data
@NoArgsConstructor
public class Stations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long station_id;
    @Column
    String station_name;
}
