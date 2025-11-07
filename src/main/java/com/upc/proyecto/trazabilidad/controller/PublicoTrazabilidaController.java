package com.upc.proyecto.trazabilidad.controller;

import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.service.EmpaqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/public/trazabilidad")
public class PublicoTrazabilidaController {

    @Autowired
    private EmpaqueService empaqueService;

    @GetMapping("/empaque")
    public ResponseEntity<?> consultarTrazabilidad(@RequestParam(required = true) String codigoQR) {
        try {
            Optional<Empaque> empaqueOpt = empaqueService.obtenerTrazabilidadCompletaPorCodigoQR(codigoQR);

            if (empaqueOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Código QR no válido."));
            }

            Empaque empaque = empaqueOpt.get();

            empaque.getTransportes().size();
            empaque.getDistribuciones().size();
            if (empaque.getLote() != null) {
                empaque.getLote().getIdLote();
            }

            return ResponseEntity.ok(empaque);

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("mensaje", e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al consultar trazabilidad.", "detalle", e.getMessage()));
        }
    }
}
