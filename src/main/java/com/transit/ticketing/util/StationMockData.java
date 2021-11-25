package com.transit.ticketing.util;

import java.util.ArrayList;
import java.util.List;

import com.transit.ticketing.entity.Location;
import com.transit.ticketing.entity.Stations;

public class StationMockData {

	 public static Stations populateStationData() {
		    Stations station = new Stations();
		    Location location = new Location();
		    
		    station.setStopId("EKM");
		    station.setStopName("Ernakulam");
		    
		    location.setStopLng("9.972397");
		    location.setStopLat("76.27833");

		    station.setLocation(location);
		    
		    return station;
		  }
	 
	 public static Stations populateStationDataById(String stationId) {
		    Stations station = new Stations();
		    Location location = new Location();
		    
		    station.setStopId(stationId);
		    station.setStopName("Embarkation");
		    
		    location.setStopLng("9.970072");
		    location.setStopLat("76.26221");

		    station.setLocation(location);
		    
		    return station;
		  }

	public static Stations populateStationDataByLocation(String lat, String lon) {
		
		Stations station = new Stations();
	    Location location = new Location();
	    
	    station.setStopId("FKO");
	    station.setStopName("Fort Kochi");
	    
	    location.setStopLng(lon);
	    location.setStopLat(lat);

	    station.setLocation(location);
	    
	    return station;
	}
	
}
