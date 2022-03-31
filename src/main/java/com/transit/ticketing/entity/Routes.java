package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="routes")
@Data
@NoArgsConstructor
public class Routes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long route_id;

    @Column
    private String route_name;

    @OneToOne
    @JoinColumn(name = "route_id" , referencedColumnName = "route_id", insertable = false, updatable = false)
    private Trips trips;
}
