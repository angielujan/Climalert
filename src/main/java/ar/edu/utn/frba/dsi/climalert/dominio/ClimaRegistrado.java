package ar.edu.utn.frba.dsi.climalert.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ClimaRegistrado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ubicacion;
	private double temperaturaCelsius;
	private double sensacionTermicaCelsius;
	private double humedadPorcentaje;
	private String condicionDescripcion;
	private double vientoKph;
	private double precipitacionMm;
	private int nubosidadPorcentaje;
	private double indiceUv;
	private LocalDateTime fechaHoraObservacion;
	private LocalDateTime fechaHoraRegistro;

	protected ClimaRegistrado() {
		// requerido por JPA
	}

	public ClimaRegistrado(String ubicacion,
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
		this.fechaHoraRegistro = LocalDateTime.now();
	}

	/**
	 * Regla de negocio central del sistema: determina si este registro
	 * climatico constituye una condicion de alerta, dados los umbrales
	 * configurados.
	 */
	public boolean esCondicionDeAlerta(double temperaturaUmbral, double humedadUmbral) {
		return this.temperaturaCelsius > temperaturaUmbral
				&& this.humedadPorcentaje > humedadUmbral;
	}

	public Long getId() {
		return id;
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

	public LocalDateTime getFechaHoraRegistro() {
		return fechaHoraRegistro;
	}
}
