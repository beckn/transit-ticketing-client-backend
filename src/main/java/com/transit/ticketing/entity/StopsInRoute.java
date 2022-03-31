package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="stops_in_route")
@Data
@NoArgsConstructor
public class StopsInRoute {
    @Column
    private long route_id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stop_id;

    @OneToOne
    @JoinColumn(name = "route_id" , referencedColumnName = "route_id", insertable = false, updatable = false)
    private Routes routes;
}
