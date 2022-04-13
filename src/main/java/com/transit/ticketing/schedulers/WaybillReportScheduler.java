package com.transit.ticketing.schedulers;

import com.transit.ticketing.service.WaybillReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@EnableScheduling
@Configuration
@EnableAsync
public class WaybillReportScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(WaybillReportScheduler.class);

    @Autowired
    WaybillReportService waybillReportService;

    @Async
    @Scheduled(cron = "${app.config.cronjob}")
    public void updateWaybillReport() throws InterruptedException {
        LOG.info("Started cron job at :" + new Date());
        try {
            waybillReportService.updateWaybillReportData();
            LOG.info("Waybill report is updated");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }
}
