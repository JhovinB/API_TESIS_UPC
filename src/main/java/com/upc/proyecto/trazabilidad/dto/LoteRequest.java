package com.upc.proyecto.trazabilidad.dto;

import java.time.LocalDate;
import java.util.List;

public class LoteRequest {
    private String codigoLote;
    private String especie;
    private LocalDate fechaCosecha;
    private boolean escanearQR;
    private List<EmpaqueRequest> empaques;

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public LocalDate getFechaCosecha() {
        return fechaCosecha;
    }

    public void setFechaCosecha(LocalDate fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public List<EmpaqueRequest> getEmpaques() {
        return empaques;
    }

    public void setEmpaques(List<EmpaqueRequest> empaques) {
        this.empaques = empaques;
    }

    public boolean isEscanearQR() {
        return escanearQR;
    }

    public void setEscanearQR(boolean escanearQR) {
        this.escanearQR = escanearQR;
    }
}
