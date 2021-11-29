package com.transit.ticketing.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class LocationDetailsDto {

	@JsonProperty("stopLat")
	private String stopLat;
	
	@JsonProperty("stopLng")
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
		LocationDetailsDto other = (LocationDetailsDto) obj;
		return Objects.equals(stopLat, other.stopLat) && Objects.equals(stopLng, other.stopLng);
	}

}
