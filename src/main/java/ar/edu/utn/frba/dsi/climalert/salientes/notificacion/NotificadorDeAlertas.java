package ar.edu.utn.frba.dsi.climalert.salientes.notificacion;

import ar.edu.utn.frba.dsi.climalert.dominio.Alerta;

public interface NotificadorDeAlertas {
	void notificar(Alerta alerta);
}
