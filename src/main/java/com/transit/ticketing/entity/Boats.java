package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    @Column
    Date purchase_dt;
    @Column
    String boat_reg_no;
    @Column
    Date last_service_dt;
    @Column
    Date new_service_dt;
    @Column
    String remarks;
    @OneToOne
    @JoinColumn(name = "station_id" , referencedColumnName = "station_id", insertable = false, updatable = false)
    Stations stations;
}
