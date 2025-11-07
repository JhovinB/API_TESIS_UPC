package com.upc.proyecto.trazabilidad.dto;

import java.time.LocalDateTime;

public class TransporteResumenResponse {

    private Long idTransporte;
    private String placaVehiculo;
    private String lugarSalida;
    private String destino;
    private LocalDateTime fechaSalida;

    public TransporteResumenResponse() {}

    public TransporteResumenResponse(Long idTransporte, String placaVehiculo, String lugarSalida, String destino, LocalDateTime fechaSalida) {
        this.idTransporte = idTransporte;
        this.placaVehiculo = placaVehiculo;
        this.lugarSalida = lugarSalida;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
    }
    public Long getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(Long idTransporte) {
        this.idTransporte = idTransporte;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }
}
