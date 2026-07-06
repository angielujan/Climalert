package ar.edu.utn.frba.dsi.climalert.servicio;

import ar.edu.utn.frba.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.frba.dsi.climalert.datos.RepositorioClimaRegistrado;
import ar.edu.utn.frba.dsi.climalert.dominio.Alerta;
import ar.edu.utn.frba.dsi.climalert.dominio.ClimaRegistrado;
import ar.edu.utn.frba.dsi.climalert.salientes.notificacion.NotificadorDeAlertas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

	@Mock
	private RepositorioClimaRegistrado repositorioClimaRegistrado;

	@Mock
	private NotificadorDeAlertas notificadorDeAlertas;

	private AlertaService alertaService;
	private ClimalertProperties properties;

	@BeforeEach
	void setUp() {
		properties = new ClimalertProperties();
		properties.getWeatherApi().setUbicacion("CABA");
		properties.getAlertas().setTemperaturaUmbral(35.0);
		properties.getAlertas().setHumedadUmbral(60.0);

		alertaService = new AlertaService(repositorioClimaRegistrado, notificadorDeAlertas, properties);
	}

	private ClimaRegistrado climaCon(double temp, double humedad) {
		return new ClimaRegistrado("CABA", temp, temp, humedad, "Soleado", 10.0, 0.0, 20, 5.0, LocalDateTime.now());
	}

	@Test
	void notificaCuandoElUltimoClimaSuperaAmbosUmbrales() {
		when(repositorioClimaRegistrado.findTopByUbicacionOrderByFechaHoraObservacionDesc("CABA"))
				.thenReturn(Optional.of(climaCon(38.0, 75.0)));

		alertaService.analizarYNotificarSiCorresponde();

		ArgumentCaptor<Alerta> captor = ArgumentCaptor.forClass(Alerta.class);
		verify(notificadorDeAlertas).notificar(captor.capture());
		assertThat(captor.getValue().getClimaQueLaOrigino().getTemperaturaCelsius()).isEqualTo(38.0);
	}

	@Test
	void noNotificaCuandoElClimaEstaDentroDeParametrosNormales() {
		when(repositorioClimaRegistrado.findTopByUbicacionOrderByFechaHoraObservacionDesc("CABA"))
				.thenReturn(Optional.of(climaCon(22.0, 45.0)));

		alertaService.analizarYNotificarSiCorresponde();

		verify(notificadorDeAlertas, never()).notificar(org.mockito.ArgumentMatchers.any());
	}

	@Test
	void noNotificaNiFallaCuandoTodaviaNoHayDatosClimaticosRegistrados() {
		when(repositorioClimaRegistrado.findTopByUbicacionOrderByFechaHoraObservacionDesc("CABA"))
				.thenReturn(Optional.empty());

		alertaService.analizarYNotificarSiCorresponde();

		verify(notificadorDeAlertas, never()).notificar(org.mockito.ArgumentMatchers.any());
	}
}
