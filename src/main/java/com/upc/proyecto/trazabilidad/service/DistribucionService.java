package com.upc.proyecto.trazabilidad.service;


import com.upc.proyecto.trazabilidad.dto.*;
import com.upc.proyecto.trazabilidad.entity.Distribucion;

import java.util.List;

public interface DistribucionService {
    DistribucionResponse registrarRecepcion(DistribucionRequest request, String correoDistribuidor);
    List<Distribucion> listarDistribuciones();
    List<DistribucionResumenResponse> listarDistribucionResumen();
    Distribucion obtenerPorId(Long id);
    List<Distribucion> listarDistribucionesPorCorreo(String correoDistribuidor);
}
