package com.upc.proyecto.trazabilidad.repository;

import com.upc.proyecto.trazabilidad.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
