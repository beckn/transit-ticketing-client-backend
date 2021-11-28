package com.transit.ticketing.dao.impl;

import com.transit.ticketing.dao.ETicketingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ETicketingDaoImpl implements ETicketingDao {
    private static final Logger LOG = LoggerFactory.getLogger(ETicketingDaoImpl.class);
    @Autowired
    private DataSource dataSource;
    private Connection connection;
    public ETicketingDaoImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }
    @Override
    public String findScheduleIdFromTripId(String tripId) throws SQLException {
        String sql = "select schedule_id from trips_in_schedule where trip_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,tripId);
        ResultSet rs = preparedStatement.executeQuery();
        String schId="";
        while(rs.next()){
            schId = rs.getString(1);
        }
        if(rs!=null){
            rs.close();
            preparedStatement.close();
        }
        return schId;
    }

    @Override
    public int findMaxCapacityForTripIdAndJourneyDate(String tripId, String date) throws SQLException {
        String sql = "SELECT CAPACITY FROM BOATS WHERE BOAT_ID=(SELECT J.BOAT_ID  FROM TRIPS_IN_SCHEDULE S join SCHEDULED_JOURNEY J on S.SCHEDULE_ID  = J.SCHEDULE_ID where S.TRIP_ID = ? and J.JOURNEY_DATE like ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,tripId);
        preparedStatement.setString(2,date+"%");
        ResultSet rs = preparedStatement.executeQuery();
        int boatId=0;
        while(rs.next()){
            boatId = rs.getInt(1);
        }
        if(rs!=null){
            rs.close();
            preparedStatement.close();
        }
        return boatId;
    }

    @Override
    public int findIssuedTicketCount(String source, String tripId, String boatId, String scheduleId, String journeyDate) throws SQLException {
        String sql = "select sum(number_of_tickets) from sales_records where ? < destination_jetty and trip_id=? and boat_id=? and schedule_id = ? and date_of_journey like ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,source);
        preparedStatement.setString(2,tripId);
        preparedStatement.setString(3,boatId);
        preparedStatement.setString(4,scheduleId);
        preparedStatement.setString(5,journeyDate+"%");
        ResultSet rs = preparedStatement.executeQuery();
        int issued=0;
        while(rs.next()){
            issued = rs.getInt(1);
        }
        if(rs!=null){
            rs.close();
            preparedStatement.close();
        }
        return issued;
    }

    @Override
    public String findBoatIdFromTripIdAndJourneyDate(String tripId, String date) throws SQLException {
        String sql = "SELECT J.BOAT_ID  FROM TRIPS_IN_SCHEDULE S join SCHEDULED_JOURNEY J on S.SCHEDULE_ID  = J.SCHEDULE_ID where S.TRIP_ID = ? and J.JOURNEY_DATE like ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,tripId);
        preparedStatement.setString(2,date+"%");
        ResultSet rs = preparedStatement.executeQuery();
        String boatId="";
        while(rs.next()){
            boatId = rs.getString(1);
        }
        if(rs!=null){
            rs.close();
            preparedStatement.close();
        }
        return boatId;
    }

    @Override
    public String[] findFareForTicket(String source, String destination) throws SQLException {
        String sql = "SELECT price,currency_type FROM FARE_ATTRIBUTES where FARE_ID1 = (Select FARE_ID from FARE_RULES where Origin_id=? and Destination_id =?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,source);
        preparedStatement.setString(2,destination);
        ResultSet rs = preparedStatement.executeQuery();
        String[] fareAttribute=new String[2];
        while(rs.next()){
            fareAttribute[0] = rs.getString(1);
            fareAttribute[1] = rs.getString(2);
        }
        if(rs!=null){
            rs.close();
            preparedStatement.close();
        }
        return fareAttribute;
    }
}
