package ar.edu.utn.frba.dsi.climalert.servicio;

import ar.edu.utn.frba.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.frba.dsi.climalert.datos.RepositorioClimaRegistrado;
import ar.edu.utn.frba.dsi.climalert.dominio.Alerta;
import ar.edu.utn.frba.dsi.climalert.dominio.ClimaRegistrado;
import ar.edu.utn.frba.dsi.climalert.salientes.notificacion.NotificadorDeAlertas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertaService {

	private static final Logger log = LoggerFactory.getLogger(AlertaService.class);

	private final RepositorioClimaRegistrado repositorioClimaRegistrado;
	private final NotificadorDeAlertas notificadorDeAlertas;
	private final ClimalertProperties properties;

	public AlertaService(RepositorioClimaRegistrado repositorioClimaRegistrado,
	                     NotificadorDeAlertas notificadorDeAlertas,
	                     ClimalertProperties properties) {
		this.repositorioClimaRegistrado = repositorioClimaRegistrado;
		this.notificadorDeAlertas = notificadorDeAlertas;
		this.properties = properties;
	}

	public void analizarYNotificarSiCorresponde() {
		String ubicacion = properties.getWeatherApi().getUbicacion();

		Optional<ClimaRegistrado> ultimoClima =
				repositorioClimaRegistrado.findTopByUbicacionOrderByFechaHoraObservacionDesc(ubicacion);

		if (ultimoClima.isEmpty()) {
			log.info("Aun no hay datos climaticos registrados para '{}'; no se analiza nada todavia.", ubicacion);
			return;
		}

		ClimaRegistrado clima = ultimoClima.get();

		boolean esAlerta = clima.esCondicionDeAlerta(
				properties.getAlertas().getTemperaturaUmbral(),
				properties.getAlertas().getHumedadUmbral()
		);

		if (!esAlerta) {
			log.debug("Clima dentro de parametros normales, no se genera alerta.");
			return;
		}

		Alerta alerta = new Alerta(clima);
		log.warn("Condicion de alerta detectada en {}. Notificando...", clima.getUbicacion());
		notificadorDeAlertas.notificar(alerta);
	}
}
