package com.upc.proyecto.trazabilidad.repository;

import com.upc.proyecto.trazabilidad.entity.Distribucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface DistribucionRepository extends JpaRepository<Distribucion, Long> {

    List<Distribucion> findByDistribuidorIdUsuario(Long idUsuario);
    boolean existsByCodigoLote(String codigoLote);
    boolean existsByEmpaques_CodigoQR(String codigoQR);

    // Encuentra las distribuciones que contienen al empaque (tabla distribucion_empaques)
    List<Distribucion> findByEmpaques_IdEmpaque(Long idEmpaque);
}
