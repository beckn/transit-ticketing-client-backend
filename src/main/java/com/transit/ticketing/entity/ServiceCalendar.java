package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="calender")
@Data
@NoArgsConstructor
public class ServiceCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long service_id;
    @Column
    private short monday;
    @Column
    short tuesday;
    @Column
    short wednesday;
    @Column
    short thursday;
    @Column
    short friday;
    @Column
    short saturday;
    @Column
    short sunday;
}
