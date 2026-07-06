package ar.edu.utn.frba.dsi.climalert.dominio;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ClimaRegistradoTest {

	private static final double UMBRAL_TEMP = 35.0;
	private static final double UMBRAL_HUM = 60.0;

	private ClimaRegistrado climaCon(double temp, double humedad) {
		return new ClimaRegistrado("CABA", temp, temp, humedad, "Soleado", 10.0, 0.0, 20, 5.0, LocalDateTime.now());
	}

	@Test
	void esAlertaCuandoTemperaturaYHumedadSuperanLosUmbrales() {
		ClimaRegistrado clima = climaCon(36.0, 65.0);

		assertThat(clima.esCondicionDeAlerta(UMBRAL_TEMP, UMBRAL_HUM)).isTrue();
	}

	@Test
	void noEsAlertaSiSoloLaTemperaturaSuperaElUmbral() {
		ClimaRegistrado clima = climaCon(40.0, 50.0);

		assertThat(clima.esCondicionDeAlerta(UMBRAL_TEMP, UMBRAL_HUM)).isFalse();
	}

	@Test
	void noEsAlertaSiSoloLaHumedadSuperaElUmbral() {
		ClimaRegistrado clima = climaCon(20.0, 90.0);

		assertThat(clima.esCondicionDeAlerta(UMBRAL_TEMP, UMBRAL_HUM)).isFalse();
	}

	@Test
	void noEsAlertaCuandoAmbosValoresEstanEnElLimiteExacto() {
		// La condición es estrictamente "mayor a", no "mayor o igual a"
		ClimaRegistrado clima = climaCon(35.0, 60.0);

		assertThat(clima.esCondicionDeAlerta(UMBRAL_TEMP, UMBRAL_HUM)).isFalse();
	}

	@Test
	void noEsAlertaConValoresNormales() {
		ClimaRegistrado clima = climaCon(22.0, 45.0);

		assertThat(clima.esCondicionDeAlerta(UMBRAL_TEMP, UMBRAL_HUM)).isFalse();
	}
}
