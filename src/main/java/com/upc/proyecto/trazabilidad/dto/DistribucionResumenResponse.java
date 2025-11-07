package com.upc.proyecto.trazabilidad.dto;

import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.entity.Transporte;
import com.upc.proyecto.trazabilidad.entity.Usuario;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DistribucionResumenResponse {
    private Long idDistribucion;
    private String codigoLote;
    private LocalDateTime fechaRecepcion;
    private String estado;

    public DistribucionResumenResponse(Long idDistribucion,String codigoLote,  String estado,LocalDateTime fechaRecepcion) {
        this.idDistribucion = idDistribucion;
        this.codigoLote = codigoLote;
        this.estado = estado;
        this.fechaRecepcion = fechaRecepcion;
    }

}
