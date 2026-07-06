package ar.edu.utn.frba.dsi.climalert.servicio;

import ar.edu.utn.frba.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.frba.dsi.climalert.datos.RepositorioClimaRegistrado;
import ar.edu.utn.frba.dsi.climalert.dominio.ClimaRegistrado;
import ar.edu.utn.frba.dsi.climalert.salientes.clima.DatosClimaExterno;
import ar.edu.utn.frba.dsi.climalert.salientes.clima.ProveedorClimaExterno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClimaServiceTest {

	@Mock
	private ProveedorClimaExterno proveedorClimaExterno;

	@Mock
	private RepositorioClimaRegistrado repositorioClimaRegistrado;

	private ClimaService climaService;

	@BeforeEach
	void setUp() {
		ClimalertProperties properties = new ClimalertProperties();
		properties.getWeatherApi().setUbicacion("CABA");

		climaService = new ClimaService(proveedorClimaExterno, repositorioClimaRegistrado, properties);
	}

	@Test
	void obtieneElClimaDelProveedorYLoPersiste() {
		DatosClimaExterno datosExternos = new DatosClimaExterno(
				"CABA", 36.5, 38.0, 70.0, "Soleado", 15.0, 2.0, 40, 6.0, LocalDateTime.now());

		when(proveedorClimaExterno.obtenerClimaActual("CABA")).thenReturn(datosExternos);
		when(repositorioClimaRegistrado.save(any(ClimaRegistrado.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		ClimaRegistrado resultado = climaService.obtenerYRegistrarClimaActual();

		assertThat(resultado.getUbicacion()).isEqualTo("CABA");
		assertThat(resultado.getTemperaturaCelsius()).isEqualTo(36.5);
		assertThat(resultado.getHumedadPorcentaje()).isEqualTo(70.0);

		verify(proveedorClimaExterno).obtenerClimaActual(eq("CABA"));
	}

	@Test
	void elRegistroPersistidoConservaLosDatosObtenidosDelProveedor() {
		DatosClimaExterno datosExternos = new DatosClimaExterno(
				"CABA", 18.0, 17.0, 40.0, "Nublado", 8.0, 0.5, 60, 1.0, LocalDateTime.now());
		when(proveedorClimaExterno.obtenerClimaActual("CABA")).thenReturn(datosExternos);
		when(repositorioClimaRegistrado.save(any(ClimaRegistrado.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		climaService.obtenerYRegistrarClimaActual();

		ArgumentCaptor<ClimaRegistrado> captor = ArgumentCaptor.forClass(ClimaRegistrado.class);
		verify(repositorioClimaRegistrado).save(captor.capture());

		assertThat(captor.getValue().getCondicionDescripcion()).isEqualTo("Nublado");
	}
}
