package com.transit.ticketing.schedulers;

import com.transit.ticketing.exception.ETicketingException;
import com.transit.ticketing.service.ScheduledTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class JourneyScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(JourneyScheduler.class);
    @Autowired
    ScheduledTask scheduledTask;
    @Scheduled(cron = "${app.config.cronjob}") //0 */1 * ? * *
    public void scheduleTaskUsingCronExpression() {
        LOG.info("Started cron job at :"+new Date());
        try {
            scheduledTask.createJourney();
            LOG.info("Completed cron job at :"+new Date());
        } catch (Exception e) {
            LOG.error("Error in occurred during cron job.",e);
            // Ignore error so that service
        }
    }
}
