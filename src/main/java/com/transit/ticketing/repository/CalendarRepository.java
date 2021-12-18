package com.transit.ticketing.repository;

import com.transit.ticketing.entity.ServiceCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<ServiceCalendar,Long> {
}
