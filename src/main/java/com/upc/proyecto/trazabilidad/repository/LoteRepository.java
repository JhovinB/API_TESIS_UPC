package com.upc.proyecto.trazabilidad.repository;

import com.upc.proyecto.trazabilidad.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
    Optional<Lote> findByCodigoLote(String codigoLote);
    boolean existsByCodigoLote(String codigoLote);
}
