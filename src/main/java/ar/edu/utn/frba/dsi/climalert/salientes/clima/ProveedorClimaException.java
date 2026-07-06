package ar.edu.utn.frba.dsi.climalert.salientes.clima;

public class ProveedorClimaException extends RuntimeException {
	public ProveedorClimaException(String message) {
		super(message);
	}

	public ProveedorClimaException(String message, Throwable cause) {
		super(message, cause);
	}
}
