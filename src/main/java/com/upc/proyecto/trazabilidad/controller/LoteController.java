package com.upc.proyecto.trazabilidad.controller;



import com.upc.proyecto.trazabilidad.blockchain.BlockchainService;
import com.upc.proyecto.trazabilidad.dto.EmpaqueRequest;
import com.upc.proyecto.trazabilidad.autentication.JWTUtils;
import com.upc.proyecto.trazabilidad.dto.LoteRequest;
import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.entity.Lote;
import com.upc.proyecto.trazabilidad.service.EmpaqueService;
import com.upc.proyecto.trazabilidad.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarLote(@RequestBody LoteRequest request,
                                           @RequestHeader("Authorization") String token) {
        try {
            if (request.getEmpaques() == null || request.getEmpaques().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        Map.of("exito", false, "mensaje", "El lote debe contener al menos un empaque.")
                );
            }

            for (EmpaqueRequest empaque : request.getEmpaques()) {
                if (empaque.getCodigoQR() == null || empaque.getCodigoQR().isBlank() ||
                        empaque.getUnidadMedida() == null || empaque.getUnidadMedida().isBlank() ||
                        empaque.getPeso().compareTo(BigDecimal.ZERO) <= 0) {
                    return ResponseEntity.badRequest().body(
                            Map.of("exito", false, "mensaje", "Cada empaque debe tener código QR, peso (>0) y unidad de medida.")
                    );
                }
            }

            // Extraer correo desde el token JWT
            String correo = jwtUtils.getCorreoFromToken(token.replace("Bearer ", ""));

            // Guardar el lote
            Lote loteGuardado = loteService.guardarLoteDesdeRequest(request, correo);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of(
                            "exito", true,
                            "mensaje", "Lote registrado correctamente con " + loteGuardado.getEmpaques().size() + " empaques.",
                            "lote", loteGuardado
                    )
            );
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(
                    Map.of("exito", false, "mensaje", ex.getMessage())
            );
        }
    }

    @GetMapping("/listar")
    public List<Lote> listarLotes() {
        return loteService.listarLotes();
    }

    @GetMapping("/{codigoLote}")
    public Optional<Lote> buscarPorCodigoLote(@PathVariable String codigoLote) {
        return loteService.obtenerPorCodigo(codigoLote);
    }

    @DeleteMapping("/{id}")
    public void eliminarLote(@PathVariable Long id) {
        loteService.eliminarLote(id);
    }
}
