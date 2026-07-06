package ar.edu.utn.frba.dsi.climalert.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiCurrentResponse {

	private Location location;
	private Current current;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Current getCurrent() {
		return current;
	}

	public void setCurrent(Current current) {
		this.current = current;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Location {
		private String name;
		private String localtime;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLocaltime() {
			return localtime;
		}

		public void setLocaltime(String localtime) {
			this.localtime = localtime;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Current {
		private double temp_c;
		private double feelslike_c;
		private double humidity;
		private double wind_kph;
		private double precip_mm;
		private int cloud;
		private double uv;
		private Condition condition;

		public double getTemp_c() {
			return temp_c;
		}

		public void setTemp_c(double temp_c) {
			this.temp_c = temp_c;
		}

		public double getFeelslike_c() {
			return feelslike_c;
		}

		public void setFeelslike_c(double feelslike_c) {
			this.feelslike_c = feelslike_c;
		}

		public double getHumidity() {
			return humidity;
		}

		public void setHumidity(double humidity) {
			this.humidity = humidity;
		}

		public double getWind_kph() {
			return wind_kph;
		}

		public void setWind_kph(double wind_kph) {
			this.wind_kph = wind_kph;
		}

		public double getPrecip_mm() {
			return precip_mm;
		}

		public void setPrecip_mm(double precip_mm) {
			this.precip_mm = precip_mm;
		}

		public int getCloud() {
			return cloud;
		}

		public void setCloud(int cloud) {
			this.cloud = cloud;
		}

		public double getUv() {
			return uv;
		}

		public void setUv(double uv) {
			this.uv = uv;
		}

		public Condition getCondition() {
			return condition;
		}

		public void setCondition(Condition condition) {
			this.condition = condition;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Condition {
		private String text;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
}
