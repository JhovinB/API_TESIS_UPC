package com.upc.proyecto.trazabilidad.service;

import com.upc.proyecto.trazabilidad.entity.Empaque;

import java.util.List;
import java.util.Optional;

public interface EmpaqueService {
    Optional<Empaque> obtenerEmpaquePorId(Long id);
    Optional<Empaque> obtenerPorCodigoQR(String codigoQR);
    List<Empaque> listarEmpaques();
    void eliminarEmpaque(Long id);

    Optional<Empaque> obtenerTrazabilidadCompletaPorCodigoQR(String codigoQR);
}
