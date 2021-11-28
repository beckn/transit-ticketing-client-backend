package com.transit.ticketing.util;

import java.util.ArrayList;
import java.util.List;

import com.transit.ticketing.entity.Location;
import com.transit.ticketing.entity.Station;

public class StationMockData {

	public static List<Station> populateStationData() {
		List<Station> stationList = new ArrayList();
		stationList.add(getStation("EKM", "Ernakulam", "9.972357", "76.27833"));
		stationList.add(getStation("EMB", "Embarkation", "9.970072", "76.26221"));
		stationList.add(getStation("FKO", "Fort Kochi", "9.969376", "76.26221"));
		return stationList;
	}

	public static List<Station> populateStationData(String origin) {
		List<Station> stationList = new ArrayList();
		stationList.add(getStation("EMB", "Embarkation", "9.970072", "76.26221"));
		stationList.add(getStation("FKO", "Fort Kochi", "9.969376", "76.26221"));
		return stationList;
	}

	private static Station getStation(String stopId, String stopName, String lat, String lng) {
		Station station = new Station();
		Location location = new Location();

		station.setStopId(stopId);
		station.setStopName(stopName);

		location.setStopLng(lat);
		location.setStopLat(lng);
		station.setLocation(location);
		return station;
	}

}
