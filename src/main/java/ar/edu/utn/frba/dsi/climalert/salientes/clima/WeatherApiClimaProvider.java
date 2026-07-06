package ar.edu.utn.frba.dsi.climalert.salientes.clima;

import ar.edu.utn.frba.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.frba.dsi.climalert.dto.WeatherApiCurrentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Component
public class WeatherApiClimaProvider implements ProveedorClimaExterno {

	private static final Logger log = LoggerFactory.getLogger(WeatherApiClimaProvider.class);

	private final RestTemplate restTemplate;
	private final ClimalertProperties properties;

	public WeatherApiClimaProvider(RestTemplate restTemplate, ClimalertProperties properties) {
		this.restTemplate = restTemplate;
		this.properties = properties;
	}

	@Override
	public DatosClimaExterno obtenerClimaActual(String ubicacion) {
		String url = UriComponentsBuilder
				.fromUriString(properties.getWeatherApi().getBaseUrl() + "/current.json")
				.queryParam("key", properties.getWeatherApi().getApiKey())
				.queryParam("q", ubicacion)
				.queryParam("aqi", "no")
				.toUriString();

		String apiKey = properties.getWeatherApi().getApiKey();
		log.debug("Consultando WeatherAPI: {}", url.replace(apiKey, "***"));

		try {
			WeatherApiCurrentResponse response = restTemplate.getForObject(url, WeatherApiCurrentResponse.class);

			if (response == null || response.getCurrent() == null) {
				throw new ProveedorClimaException(
						"WeatherAPI respondio sin datos de clima para la ubicacion: " + ubicacion);
			}

			WeatherApiCurrentResponse.Current current = response.getCurrent();

			return new DatosClimaExterno(
					ubicacion,
					current.getTemp_c(),
					current.getFeelslike_c(),
					current.getHumidity(),
					current.getCondition() != null ? current.getCondition().getText() : "Desconocida",
					current.getWind_kph(),
					current.getPrecip_mm(),
					current.getCloud(),
					current.getUv(),
					LocalDateTime.now()
			);

		} catch (RestClientException ex) {
			log.error("Error al consultar WeatherAPI para la ubicacion '{}': {}", ubicacion, ex.getMessage());
			throw new ProveedorClimaException("No se pudo obtener el clima desde WeatherAPI", ex);
		}
	}
}