package com.upc.proyecto.trazabilidad.repository;

import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpaqueRepository extends JpaRepository<Empaque, Long> {
    Optional<Empaque> findByCodigoQR(String codigoQR);
}
