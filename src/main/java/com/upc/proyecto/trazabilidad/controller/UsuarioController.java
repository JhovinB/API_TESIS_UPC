package com.upc.proyecto.trazabilidad.controller;


import com.upc.proyecto.trazabilidad.entity.Usuario;
import com.upc.proyecto.trazabilidad.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
    }
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

