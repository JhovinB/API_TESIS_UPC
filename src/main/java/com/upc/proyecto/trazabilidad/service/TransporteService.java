package com.upc.proyecto.trazabilidad.service;

import com.upc.proyecto.trazabilidad.dto.TransporteRequest;
import com.upc.proyecto.trazabilidad.dto.TransporteResponse;
import com.upc.proyecto.trazabilidad.dto.TransporteResumenResponse;
import com.upc.proyecto.trazabilidad.entity.Transporte;

import java.util.List;
import java.util.Optional;

public interface TransporteService {
    TransporteResponse registrarTransporte(TransporteRequest request, String correoTransportista) throws Exception;
    List<TransporteResponse> listarTransportesCompleto();
    List<TransporteResumenResponse> listarTransportes();
    TransporteResponse obtenerPorId(Long id);

}
