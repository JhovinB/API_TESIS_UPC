package com.upc.proyecto.trazabilidad.controller;

import com.upc.proyecto.trazabilidad.autentication.JWTUtils;
import com.upc.proyecto.trazabilidad.dto.TransporteRequest;
import com.upc.proyecto.trazabilidad.dto.TransporteResponse;
import com.upc.proyecto.trazabilidad.dto.TransporteResumenResponse;
import com.upc.proyecto.trazabilidad.entity.CargaTransporte;
import com.upc.proyecto.trazabilidad.entity.Transporte;
import com.upc.proyecto.trazabilidad.service.TransporteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transportes")
public class TransporteController {

    @Autowired
    private TransporteService transporteService;
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody TransporteRequest request, HttpServletRequest servletRequest) throws Exception {
        try {
            String token = jwtUtils.getTokenFromRequest(servletRequest);
            String correo = jwtUtils.getCorreoFromToken(token);

            // El servicio devuelve un DTO de respuesta, no la entidad
            TransporteResponse response = transporteService.registrarTransporte(request, correo);

            return ResponseEntity.ok(Map.of(
                    "exito", true,
                    "mensaje", "Transporte registrado correctamente",
                    "data", response
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "exito", false,
                    "mensaje", e.getMessage()
            ));
        }
    }
    @GetMapping("/listar/completo")
    public ResponseEntity<?> listarTransportesCompleto() {
        try {
            List<TransporteResponse> transportes = transporteService.listarTransportesCompleto();
            return ResponseEntity.ok(Map.of(
                    "exito", true,
                    "mensaje", "Lista de transportes obtenida correctamente",
                    "transportes", transportes
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "exito", false,
                    "mensaje", e.getMessage()
            ));
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarTransportes() {
        try {
            List<TransporteResumenResponse> transportes = transporteService.listarTransportes();
            return ResponseEntity.ok(Map.of(
                    "exito", true,
                    "mensaje", "Lista de transportes obtenida correctamente",
                    "transportes", transportes
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "exito", false,
                    "mensaje", e.getMessage()
            ));
        }
    }
    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> obtenerDetalle(@PathVariable Long id) {
        try {
            TransporteResponse detalle = transporteService.obtenerPorId(id);
            return ResponseEntity.ok(Map.of(
                    "exito", true,
                    "mensaje", "Detalle obtenido correctamente",
                    "data", detalle
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "exito", false,
                    "mensaje", e.getMessage()
            ));
        }
    }
}
