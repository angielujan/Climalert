package ar.edu.utn.frba.dsi.climalert.dominio;

public class Alerta {

	private final ClimaRegistrado climaQueLaOrigino;

	public Alerta(ClimaRegistrado climaQueLaOrigino) {
		this.climaQueLaOrigino = climaQueLaOrigino;
	}

	public ClimaRegistrado getClimaQueLaOrigino() {
		return climaQueLaOrigino;
	}

	public String detalleCompleto() {
		return String.format(
				"Se detecto una condicion climatica de alerta en %s.%n" +
						"Fecha/hora de observacion: %s%n" +
						"%n" +
						"Temperatura: %.1f C%n" +
						"Sensacion termica: %.1f C%n" +
						"Humedad: %.1f %%%n" +
						"Condicion: %s%n" +
						"Viento: %.1f km/h%n" +
						"Precipitacion: %.1f mm%n" +
						"Nubosidad: %d %%%n" +
						"Indice UV: %.1f",
				climaQueLaOrigino.getUbicacion(),
				climaQueLaOrigino.getFechaHoraObservacion(),
				climaQueLaOrigino.getTemperaturaCelsius(),
				climaQueLaOrigino.getSensacionTermicaCelsius(),
				climaQueLaOrigino.getHumedadPorcentaje(),
				climaQueLaOrigino.getCondicionDescripcion(),
				climaQueLaOrigino.getVientoKph(),
				climaQueLaOrigino.getPrecipitacionMm(),
				climaQueLaOrigino.getNubosidadPorcentaje(),
				climaQueLaOrigino.getIndiceUv()
		);
	}
}
