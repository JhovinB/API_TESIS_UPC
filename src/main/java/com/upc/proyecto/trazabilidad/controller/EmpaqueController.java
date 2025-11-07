package com.upc.proyecto.trazabilidad.controller;

import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.service.EmpaqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empaques")
public class EmpaqueController {

    @Autowired
    private EmpaqueService empaqueService;

    @GetMapping("/{codigoQR}")
    public Optional<Empaque> buscarPorCodigoQR(@PathVariable String codigoQR) {
        return empaqueService.obtenerPorCodigoQR(codigoQR);
    }
    @GetMapping("/listar")
    public ResponseEntity<List<Empaque>> listarEmpaques() {
        return ResponseEntity.ok(empaqueService.listarEmpaques());
    }
    @DeleteMapping("/{id}")
    public void eliminarEmpaque(@PathVariable Long id) {
        empaqueService.eliminarEmpaque(id);
    }
}
