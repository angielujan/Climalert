package ar.edu.utn.frba.dsi.climalert.salientes.clima;

import java.time.LocalDateTime;

public class DatosClimaExterno {
	private final String ubicacion;
	private final double temperaturaCelsius;
	private final double sensacionTermicaCelsius;
	private final double humedadPorcentaje;
	private final String condicionDescripcion;
	private final double vientoKph;
	private final double precipitacionMm;
	private final int nubosidadPorcentaje;
	private final double indiceUv;
	private final LocalDateTime fechaHoraObservacion;

	public DatosClimaExterno(String ubicacion,
	                         double temperaturaCelsius,
	                         double sensacionTermicaCelsius,
	                         double humedadPorcentaje,
	                         String condicionDescripcion,
	                         double vientoKph,
	                         double precipitacionMm,
	                         int nubosidadPorcentaje,
	                         double indiceUv,
	                         LocalDateTime fechaHoraObservacion) {
		this.ubicacion = ubicacion;
		this.temperaturaCelsius = temperaturaCelsius;
		this.sensacionTermicaCelsius = sensacionTermicaCelsius;
		this.humedadPorcentaje = humedadPorcentaje;
		this.condicionDescripcion = condicionDescripcion;
		this.vientoKph = vientoKph;
		this.precipitacionMm = precipitacionMm;
		this.nubosidadPorcentaje = nubosidadPorcentaje;
		this.indiceUv = indiceUv;
		this.fechaHoraObservacion = fechaHoraObservacion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public double getTemperaturaCelsius() {
		return temperaturaCelsius;
	}

	public double getSensacionTermicaCelsius() {
		return sensacionTermicaCelsius;
	}

	public double getHumedadPorcentaje() {
		return humedadPorcentaje;
	}

	public String getCondicionDescripcion() {
		return condicionDescripcion;
	}

	public double getVientoKph() {
		return vientoKph;
	}

	public double getPrecipitacionMm() {
		return precipitacionMm;
	}

	public int getNubosidadPorcentaje() {
		return nubosidadPorcentaje;
	}

	public double getIndiceUv() {
		return indiceUv;
	}

	public LocalDateTime getFechaHoraObservacion() {
		return fechaHoraObservacion;
	}
}
