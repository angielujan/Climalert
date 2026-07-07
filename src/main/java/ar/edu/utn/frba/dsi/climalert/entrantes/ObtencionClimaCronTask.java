package ar.edu.utn.frba.dsi.climalert.entrantes;

import ar.edu.utn.frba.dsi.climalert.servicio.ClimaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ObtencionClimaCronTask {

	private static final Logger log = LoggerFactory.getLogger(ObtencionClimaCronTask.class);

	private final ClimaService climaService;

	public ObtencionClimaCronTask(ClimaService climaService) {
		this.climaService = climaService;
	}

	@Scheduled(cron = "${climalert.scheduling.obtencion-clima-cron}")
	public void ejecutar() {
		try {
			climaService.obtenerYRegistrarClimaActual();
		} catch (Exception ex) {
			log.error("Error al obtener/registrar el clima actual: {}", ex.getMessage(), ex);
		}
	}
}
