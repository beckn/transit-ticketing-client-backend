package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="boats")
@Data
@NoArgsConstructor
public class Boats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boat_id;
    @Column
    private int capacity;
    @Column
    private long station_id;
    @Column
    boolean active;

}
