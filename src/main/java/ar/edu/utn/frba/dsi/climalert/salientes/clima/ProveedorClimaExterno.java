package ar.edu.utn.frba.dsi.climalert.salientes.clima;

public interface ProveedorClimaExterno {
	DatosClimaExterno obtenerClimaActual(String ubicacion);
}
