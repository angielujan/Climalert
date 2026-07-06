package ar.edu.utn.frba.dsi.climalert.entrantes;

import ar.edu.utn.frba.dsi.climalert.servicio.AlertaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnalisisAlertasCronTask {

	private static final Logger log = LoggerFactory.getLogger(AnalisisAlertasCronTask.class);

	private final AlertaService alertaService;

	public AnalisisAlertasCronTask(AlertaService alertaService) {
		this.alertaService = alertaService;
	}

	@Scheduled(cron = "${climalert.scheduling.analisis-alertas-cron}")
	public void ejecutar() {
		try {
			alertaService.analizarYNotificarSiCorresponde();
		} catch (Exception ex) {
			log.error("Error al analizar/notificar alertas: {}", ex.getMessage(), ex);
		}
	}
}
