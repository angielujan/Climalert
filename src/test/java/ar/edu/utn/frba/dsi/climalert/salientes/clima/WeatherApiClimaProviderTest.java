package ar.edu.utn.frba.dsi.climalert.salientes.clima;

import ar.edu.utn.frba.dsi.climalert.config.ClimalertProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.http.HttpMethod.GET;

class WeatherApiClimaProviderTest {

	private MockRestServiceServer mockServer;
	private WeatherApiClimaProvider provider;

	@BeforeEach
	void setUp() {
		RestTemplate restTemplate = new RestTemplate();
		mockServer = MockRestServiceServer.createServer(restTemplate);

		ClimalertProperties properties = new ClimalertProperties();
		properties.getWeatherApi().setBaseUrl("https://api.weatherapi.com/v1");
		properties.getWeatherApi().setApiKey("FAKE_KEY");
		properties.getWeatherApi().setUbicacion("CABA");

		provider = new WeatherApiClimaProvider(restTemplate, properties);
	}

	@Test
	void traduceCorrectamenteLaRespuestaDeWeatherApi() {
		String jsonRespuesta = """
				{
				  "location": { "name": "Buenos Aires", "localtime": "2026-07-03 10:00" },
				  "current": {
				    "temp_c": 36.2,
				    "feelslike_c": 39.5,
				    "humidity": 72,
				    "wind_kph": 18.4,
				    "precip_mm": 0.0,
				    "cloud": 25,
				    "uv": 7.0,
				    "condition": { "text": "Soleado" }
				  }
				}
				""";

		mockServer.expect(method(GET))
				.andExpect(requestTo(org.hamcrest.Matchers.containsString("/current.json")))
				.andRespond(withSuccess(jsonRespuesta, MediaType.APPLICATION_JSON));

		DatosClimaExterno resultado = provider.obtenerClimaActual("CABA");

		assertThat(resultado.getUbicacion()).isEqualTo("Buenos Aires");
		assertThat(resultado.getTemperaturaCelsius()).isEqualTo(36.2);
		assertThat(resultado.getSensacionTermicaCelsius()).isEqualTo(39.5);
		assertThat(resultado.getHumedadPorcentaje()).isEqualTo(72);
		assertThat(resultado.getVientoKph()).isEqualTo(18.4);
		assertThat(resultado.getNubosidadPorcentaje()).isEqualTo(25);
		assertThat(resultado.getIndiceUv()).isEqualTo(7.0);
		assertThat(resultado.getCondicionDescripcion()).isEqualTo("Soleado");

		mockServer.verify();
	}
}
