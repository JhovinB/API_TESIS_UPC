package com.upc.proyecto.trazabilidad.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TransporteRequest {

    private String placaVehiculo;
    private String lugarSalida;
    private String destino;
    private LocalDateTime fechaSalida;
    private String descripcion;

    // Lista de códigos QR de los empaques que serán transportados
    private List<String> codigosQR;

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

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getCodigosQR() {
        return codigosQR;
    }

    public void setCodigosQR(List<String> codigosQR) {
        this.codigosQR = codigosQR;
    }
}
