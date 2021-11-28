package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="fare_rules")
@Data
@NoArgsConstructor
public class FareRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fare_id;
    @Column
    private long origin_id;
    @Column
    private long destination_id;
}
