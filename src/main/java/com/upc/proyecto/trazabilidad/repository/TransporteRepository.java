package com.upc.proyecto.trazabilidad.repository;

import com.upc.proyecto.trazabilidad.entity.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte,Long> {

    List<Transporte> findByEmpaques_IdEmpaque(Long idEmpaque);
}
