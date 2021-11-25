package com.transit.ticketing.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Stations {

	@JsonProperty("id")
	private String stopId;
	
	@JsonProperty("name")
	private String stopName;
	
	@JsonProperty("location")
	private Location location;

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		return Objects.hash(location, stopId, stopName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stations other = (Stations) obj;
		return Objects.equals(location, other.location) && Objects.equals(stopId, other.stopId)
				&& Objects.equals(stopName, other.stopName);
	}
	
}
