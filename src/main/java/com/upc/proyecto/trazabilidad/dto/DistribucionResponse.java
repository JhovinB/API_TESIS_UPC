package com.upc.proyecto.trazabilidad.dto;

import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.entity.Transporte;
import com.upc.proyecto.trazabilidad.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class DistribucionResponse {

    private Long idDistribucion;
    private String codigoLote;
    private String descripcion;
    private LocalDateTime fechaRecepcion;
    private String estado;
    private String hashBlockchain;
    private String nombreDistribuidor;
    private List<String> codigosQR;
    private LocalDateTime fechaRegistro;
    private Usuario distribuidor;
    private Transporte transporte;
    private List<Empaque> empaques;


    public List<String> getCodigosQR() {
        return codigosQR;
    }

    public void setCodigosQR(List<String> codigosQR) {
        this.codigosQR = codigosQR;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public LocalDateTime getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDateTime fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Long getIdDistribucion() {
        return idDistribucion;
    }

    public void setIdDistribucion(Long idDistribucion) {
        this.idDistribucion = idDistribucion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHashBlockchain() {
        return hashBlockchain;
    }

    public void setHashBlockchain(String hashBlockchain) {
        this.hashBlockchain = hashBlockchain;
    }

    public String getNombreDistribuidor() {
        return nombreDistribuidor;
    }

    public void setNombreDistribuidor(String nombreDistribuidor) {
        this.nombreDistribuidor = nombreDistribuidor;
    }

    public List<Empaque> getEmpaques() {
        return empaques;
    }

    public void setEmpaques(List<Empaque> empaques) {
        this.empaques = empaques;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public Usuario getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Usuario distribuidor) {
        this.distribuidor = distribuidor;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
