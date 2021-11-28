package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="fare_rules")
@Data
@NoArgsConstructor
public class FareAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private long fare_id1;
    @Column
    private long price;
    @Column
    private String currency_type;
}
