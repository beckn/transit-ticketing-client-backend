package com.transit.ticketing.service.serviceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.transit.ticketing.entity.Location;
import com.transit.ticketing.entity.Station;
import com.transit.ticketing.service.StationService;

@Service
public class StationServiceImpl implements StationService {

	 @Value("${spring.datasource.driverClassName}")
	 private String myDriver;
	 
	 @Value("${spring.datasource.url}")
	 private String myUrl;
	 
	 @Value("${spring.datasource.username}")
	 private String username;
	 
	@Override
	public ResponseEntity<List<Station>> getStationsById(String origin) {
	 
		List<Station> stations = new ArrayList();
		try {
		
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, "");

			if (origin != null && !origin.isEmpty()) {
				// Stations by Origin
				// stations = StationMockData.populateStationData(origin);
				String query = "select STOP_ID,STOP_NAME, STOP_LAT, STOP_LON from STOPS where stop_id in (select distinct destination.stop_id from stop_times origin, stop_times destination where origin.trip_id = destination.trip_id and origin.stop_sequence < destination.stop_sequence and origin.stop_id = "+origin+")";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Station station = new Station();
					Location location = new Location();
					station.setStopId(rs.getString("stop_id"));
					station.setStopName(rs.getString("stop_name"));
					location.setStopLng(rs.getString("stop_lon"));
					location.setStopLat(rs.getString("stop_lat"));
					station.setLocation(location);
					stations.add(station);
				}
				st.close();
			} else {
				// stations = StationMockData.populateStationData();
				String query = "select STOP_ID,STOP_NAME, STOP_LAT, STOP_LON from STOPS";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Station station = new Station();
					Location location = new Location();
					station.setStopId(rs.getString("stop_id"));
					station.setStopName(rs.getString("stop_name"));
					location.setStopLng(rs.getString("stop_lon"));
					location.setStopLat(rs.getString("stop_lat"));
					station.setLocation(location);
					stations.add(station);
				}
				st.close();
			}

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(stations);
	}

}
