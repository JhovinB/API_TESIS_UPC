package com.upc.proyecto.trazabilidad.controller;

import com.upc.proyecto.trazabilidad.autentication.JWTUtils;
import com.upc.proyecto.trazabilidad.dto.DistribucionRequest;
import com.upc.proyecto.trazabilidad.dto.DistribucionResponse;
import com.upc.proyecto.trazabilidad.dto.DistribucionResumenResponse;
import com.upc.proyecto.trazabilidad.dto.TransporteResumenResponse;
import com.upc.proyecto.trazabilidad.entity.Distribucion;
import com.upc.proyecto.trazabilidad.service.DistribucionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/distribuciones")
public class DistribucionController {

    @Autowired
    private DistribucionService distribucionService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody DistribucionRequest request, HttpServletRequest servletRequest) {
        try {
            // Obtener correo desde el token JWT
            String token = jwtUtils.getTokenFromRequest(servletRequest);
            String correo = jwtUtils.getCorreoFromToken(token);

            DistribucionResponse response = distribucionService.registrarRecepcion(request, correo);

            return ResponseEntity.ok(Map.of(
                    "exito", true,
                    "mensaje", "Recepción registrada correctamente",
                    "data", response
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "exito", false,
                    "mensaje", e.getMessage()
            ));
        }
    }

    @GetMapping("/listarCompleto")
    public ResponseEntity<?> listarDistribuciones() {
        try {
            List<Distribucion> distribuciones = distribucionService.listarDistribuciones();
            return ResponseEntity.ok(Map.of(
                    "exito", true,
                    "mensaje", "Lista de distribuciones obtenida correctamente",
                    "distribuciones", distribuciones
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "exito", false,
                    "mensaje", e.getMessage()
            ));
        }
    }
    @GetMapping("/listar")
    public ResponseEntity<?> listarDistribucionesResumido() {
        try {
            List<DistribucionResumenResponse> transportes = distribucionService.listarDistribucionResumen();
            return ResponseEntity.ok(Map.of(
                    "exito", true,
                    "mensaje", "Lista de distribuciones obtenida correctamente",
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
            Distribucion distribucion = distribucionService.obtenerPorId(id);
            return ResponseEntity.ok(Map.of(
                    "exito", true,
                    "mensaje", "Detalle de distribución obtenido correctamente",
                    "data", distribucion
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "exito", false,
                    "mensaje", e.getMessage()
            ));
        }
    }
    @GetMapping("/recepciones")
    public ResponseEntity<?> listarMisDistribuciones(HttpServletRequest servletRequest) {
        try {
            // 🔹 Obtener el correo desde el token JWT
            String token = jwtUtils.getTokenFromRequest(servletRequest);
            String correo = jwtUtils.getCorreoFromToken(token);

            // 🔹 Filtrar por distribuidor
            List<Distribucion> distribuciones = distribucionService.listarDistribucionesPorCorreo(correo);

            return ResponseEntity.ok(Map.of(
                    "exito", true,
                    "mensaje", "Lista de recepciones obtenida correctamente para el distribuidor autenticado.",
                    "distribuciones", distribuciones
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "exito", false,
                    "mensaje", e.getMessage()
            ));
        }
    }
}
