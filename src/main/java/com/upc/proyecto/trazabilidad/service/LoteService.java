package com.upc.proyecto.trazabilidad.service;

import com.upc.proyecto.trazabilidad.dto.LoteRequest;
import com.upc.proyecto.trazabilidad.entity.Lote;

import java.util.List;
import java.util.Optional;

public interface LoteService {

    Lote guardarLote(Lote lote);
    Lote guardarLoteDesdeRequest(LoteRequest request, String correoProductor);
    Optional<Lote> obtenerLotePorId(Long id);
    Optional<Lote> obtenerPorCodigo(String codigoLote);
    List<Lote> listarLotes();
    void eliminarLote(Long id);

}
