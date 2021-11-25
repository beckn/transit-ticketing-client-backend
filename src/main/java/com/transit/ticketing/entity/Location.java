package com.transit.ticketing.entity;

import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Location {

	private String stopLat;
	
	private String stopLng;

	public String getStopLat() {
		return stopLat;
	}

	public void setStopLat(String stopLat) {
		this.stopLat = stopLat;
	}

	public String getStopLng() {
		return stopLng;
	}

	public void setStopLng(String stopLng) {
		this.stopLng = stopLng;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stopLat, stopLng);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(stopLat, other.stopLat) && Objects.equals(stopLng, other.stopLng);
	}

}
