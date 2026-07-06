package ar.edu.utn.frba.dsi.climalert.datos;

import ar.edu.utn.frba.dsi.climalert.dominio.ClimaRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioClimaRegistrado extends JpaRepository<ClimaRegistrado, Long> {
	Optional<ClimaRegistrado> findTopByUbicacionOrderByFechaHoraObservacionDesc(String ubicacion);
}
