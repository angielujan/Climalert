package ar.edu.utn.frba.dsi.climalert.salientes.notificacion;

import ar.edu.utn.frba.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.frba.dsi.climalert.dominio.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificadorDeAlertas implements NotificadorDeAlertas {

	private static final Logger log = LoggerFactory.getLogger(EmailNotificadorDeAlertas.class);

	private final JavaMailSender mailSender;
	private final ClimalertProperties properties;

	public EmailNotificadorDeAlertas(JavaMailSender mailSender, ClimalertProperties properties) {
		this.mailSender = mailSender;
		this.properties = properties;
	}

	@Override
	public void notificar(Alerta alerta) {
		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setFrom(properties.getAlertas().getRemitente());
		mensaje.setTo(properties.getAlertas().getDestinatarios().toArray(new String[0]));
		mensaje.setSubject("[Climalert] Alerta meteorologica en " + alerta.getClimaQueLaOrigino().getUbicacion());
		mensaje.setText(alerta.detalleCompleto());

		try {
			mailSender.send(mensaje);
			log.info("Alerta notificada por correo a: {}", properties.getAlertas().getDestinatarios());
		} catch (Exception ex) {
			log.error("No se pudo enviar el correo de alerta: {}", ex.getMessage());
			throw new NotificacionException("Fallo el envio de la notificacion de alerta por correo", ex);
		}
	}
}
