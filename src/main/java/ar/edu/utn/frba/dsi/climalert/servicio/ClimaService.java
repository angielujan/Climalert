package ar.edu.utn.frba.dsi.climalert.servicio;

import ar.edu.utn.frba.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.frba.dsi.climalert.datos.RepositorioClimaRegistrado;
import ar.edu.utn.frba.dsi.climalert.dominio.ClimaRegistrado;
import ar.edu.utn.frba.dsi.climalert.salientes.clima.DatosClimaExterno;
import ar.edu.utn.frba.dsi.climalert.salientes.clima.ProveedorClimaExterno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClimaService {

	private static final Logger log = LoggerFactory.getLogger(ClimaService.class);

	private final ProveedorClimaExterno proveedorClimaExterno;
	private final RepositorioClimaRegistrado repositorioClimaRegistrado;
	private final ClimalertProperties properties;

	public ClimaService(ProveedorClimaExterno proveedorClimaExterno,
	                    RepositorioClimaRegistrado repositorioClimaRegistrado,
	                    ClimalertProperties properties) {
		this.proveedorClimaExterno = proveedorClimaExterno;
		this.repositorioClimaRegistrado = repositorioClimaRegistrado;
		this.properties = properties;
	}

	public ClimaRegistrado obtenerYRegistrarClimaActual() {
		String ubicacion = properties.getWeatherApi().getUbicacion();

		DatosClimaExterno datos = proveedorClimaExterno.obtenerClimaActual(ubicacion);

		ClimaRegistrado registro = new ClimaRegistrado(
				datos.getUbicacion(),
				datos.getTemperaturaCelsius(),
				datos.getSensacionTermicaCelsius(),
				datos.getHumedadPorcentaje(),
				datos.getCondicionDescripcion(),
				datos.getVientoKph(),
				datos.getPrecipitacionMm(),
				datos.getNubosidadPorcentaje(),
				datos.getIndiceUv(),
				datos.getFechaHoraObservacion()
		);

		ClimaRegistrado registroGuardado = repositorioClimaRegistrado.save(registro);
		log.info("Clima registrado: {} - {} C / {}% humedad", registroGuardado.getUbicacion(),
				registroGuardado.getTemperaturaCelsius(), registroGuardado.getHumedadPorcentaje());

		return registroGuardado;
	}
}
