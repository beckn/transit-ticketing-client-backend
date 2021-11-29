package com.transit.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stops")
@Data
@NoArgsConstructor
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String stop_id;

	@Column
	private String stop_name;

	@Column
	private String stop_lat;

	@Column
	private String stop_lon;

	public String getStop_id() {
		return stop_id;
	}

	public void setStop_id(String stop_id) {
		this.stop_id = stop_id;
	}

	public String getStop_name() {
		return stop_name;
	}

	public void setStop_name(String stop_name) {
		this.stop_name = stop_name;
	}

	public String getStop_lat() {
		return stop_lat;
	}

	public void setStop_lat(String stop_lat) {
		this.stop_lat = stop_lat;
	}

	public String getStop_lon() {
		return stop_lon;
	}

	public void setStop_lon(String stop_lon) {
		this.stop_lon = stop_lon;
	}

}
