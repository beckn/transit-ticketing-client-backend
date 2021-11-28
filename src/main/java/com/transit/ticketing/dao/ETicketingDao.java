package com.transit.ticketing.dao;

import java.sql.SQLException;

public interface ETicketingDao {
    String findScheduleIdFromTripId(String tripId) throws SQLException;
    int findMaxCapacityForTripIdAndJourneyDate(String scheduleId, String date) throws SQLException;
    int findIssuedTicketCount(String source,String tripId,String boatId,String scheduleId,String journeyDate) throws SQLException;
    String findBoatIdFromTripIdAndJourneyDate(String tripId,String date) throws SQLException;
    String[] findFareForTicket(String source, String destination) throws SQLException;
}
