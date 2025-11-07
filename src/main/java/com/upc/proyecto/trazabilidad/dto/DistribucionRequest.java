package com.upc.proyecto.trazabilidad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DistribucionRequest {

    @NotBlank(message = "La descripción no puede estar vacía.")
    private String descripcion;
    @NotEmpty(message = "Debe incluir al menos un código QR de empaque.")
    private List<String> codigosQR;

    public List<String> getCodigosQR() {
        return codigosQR;
    }

    public void setCodigosQR(List<String> codigosQR) {
        this.codigosQR = codigosQR;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
