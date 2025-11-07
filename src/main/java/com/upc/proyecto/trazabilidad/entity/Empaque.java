package com.upc.proyecto.trazabilidad.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empaques")
@NoArgsConstructor
@AllArgsConstructor
public class Empaque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpaque;

    @Column(nullable = false, unique = true)
    private String codigoQR;

    @Column(nullable = false)
    private BigDecimal peso;

    @Column(nullable = false)
    private String unidadMedida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lote_id")
    @JsonIgnoreProperties({"empaques", "productor"})
    private Lote lote; // Asociación al lote

    // Transportes ya está definido como:
    @ManyToMany(mappedBy = "empaques",fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"empaques", "cargas"})
    private List<Transporte> transportes = new ArrayList<>();


    // Nueva relación para distribuciones
    @ManyToMany(mappedBy = "empaques", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"empaques", "transporte", "distribuidor"})
    private List<Distribucion> distribuciones = new ArrayList<>();


    public Long getIdEmpaque() {
        return idEmpaque;
    }

    public void setIdEmpaque(Long idEmpaque) {
        this.idEmpaque = idEmpaque;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public List<Transporte> getTransportes() {
        return transportes;
    }

    public void setTransportes(List<Transporte> transportes) {
        this.transportes = transportes;
    }

    public List<Distribucion> getDistribuciones() {
        return distribuciones;
    }

    public void setDistribuciones(List<Distribucion> distribuciones) {
        this.distribuciones = distribuciones;
    }
}
