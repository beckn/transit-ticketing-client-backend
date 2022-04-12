package com.transit.ticketing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Entity
@Table(name = "staff")
@Data
@NoArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long staff_id;
    private String staff_name;
    private long station_id;
    private String role;
    private String doj;
    private String position;
    private String shift;
    @OneToOne
    @JoinColumn(name = "station_id" , referencedColumnName = "station_id", insertable = false, updatable = false)
    Stations stations;

}
