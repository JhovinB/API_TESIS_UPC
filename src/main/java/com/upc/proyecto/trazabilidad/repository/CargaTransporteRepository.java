package com.upc.proyecto.trazabilidad.repository;

import com.upc.proyecto.trazabilidad.entity.CargaTransporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CargaTransporteRepository extends JpaRepository<CargaTransporte, Long> {
    boolean existsByEmpaqueIdEmpaque(Long idEmpaque);
    List<CargaTransporte> findByTransporteIdTransporte(Long id);
    Optional<CargaTransporte> findFirstByEmpaqueIdEmpaque(Long idEmpaque);
}
