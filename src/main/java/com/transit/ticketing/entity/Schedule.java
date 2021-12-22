package com.transit.ticketing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="schedule")
@Data
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long schedule_id;
    @Column
    private Date start_date;
    @Column
    private Date end_date;
    @Column
    private int active;
}
