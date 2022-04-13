package com.transit.ticketing.repository;

import com.transit.ticketing.entity.WayBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaybillReportRepository extends JpaRepository<WayBillReport, Long> {
}
