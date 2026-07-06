package ar.edu.utn.frba.dsi.climalert.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "climalert")
public class ClimalertProperties {

	private WeatherApi weatherApi = new WeatherApi();
	private Alertas alertas = new Alertas();
	private Scheduling scheduling = new Scheduling();

	public WeatherApi getWeatherApi() {
		return weatherApi;
	}

	public void setWeatherApi(WeatherApi weatherApi) {
		this.weatherApi = weatherApi;
	}

	public Alertas getAlertas() {
		return alertas;
	}

	public void setAlertas(Alertas alertas) {
		this.alertas = alertas;
	}

	public Scheduling getScheduling() {
		return scheduling;
	}

	public void setScheduling(Scheduling scheduling) {
		this.scheduling = scheduling;
	}

	public static class WeatherApi {
		private String baseUrl;
		private String apiKey;
		private String ubicacion;

		public String getBaseUrl() {
			return baseUrl;
		}

		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}

		public String getApiKey() {
			return apiKey;
		}

		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}

		public String getUbicacion() {
			return ubicacion;
		}

		public void setUbicacion(String ubicacion) {
			this.ubicacion = ubicacion;
		}
	}

	public static class Alertas {
		private double temperaturaUmbral;
		private double humedadUmbral;
		private List<String> destinatarios;
		private String remitente;

		public double getTemperaturaUmbral() {
			return temperaturaUmbral;
		}

		public void setTemperaturaUmbral(double temperaturaUmbral) {
			this.temperaturaUmbral = temperaturaUmbral;
		}

		public double getHumedadUmbral() {
			return humedadUmbral;
		}

		public void setHumedadUmbral(double humedadUmbral) {
			this.humedadUmbral = humedadUmbral;
		}

		public List<String> getDestinatarios() {
			return destinatarios;
		}

		public void setDestinatarios(List<String> destinatarios) {
			this.destinatarios = destinatarios;
		}

		public String getRemitente() {
			return remitente;
		}

		public void setRemitente(String remitente) {
			this.remitente = remitente;
		}
	}

	public static class Scheduling {
		private String obtencionClimaCron;
		private String analisisAlertasCron;

		public String getObtencionClimaCron() {
			return obtencionClimaCron;
		}

		public void setObtencionClimaCron(String obtencionClimaCron) {
			this.obtencionClimaCron = obtencionClimaCron;
		}

		public String getAnalisisAlertasCron() {
			return analisisAlertasCron;
		}

		public void setAnalisisAlertasCron(String analisisAlertasCron) {
			this.analisisAlertasCron = analisisAlertasCron;
		}
	}
}
