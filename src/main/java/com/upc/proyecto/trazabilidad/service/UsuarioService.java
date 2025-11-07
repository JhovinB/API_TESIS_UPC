package com.upc.proyecto.trazabilidad.service;

import com.upc.proyecto.trazabilidad.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario guardarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Optional<Usuario> obtenerPorId(Long id);
    void eliminarUsuario(Long id);

}
