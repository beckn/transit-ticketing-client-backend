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
    @Column
    private String staff_name;
    @Column
    private long station_id;
    @Column
    private String role;

}
