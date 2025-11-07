package com.upc.proyecto.trazabilidad.repository;

import com.upc.proyecto.trazabilidad.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
