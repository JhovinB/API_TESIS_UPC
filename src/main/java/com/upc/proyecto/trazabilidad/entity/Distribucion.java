package com.upc.proyecto.trazabilidad.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "distribuciones")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Distribucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDistribucion;

    @Column(nullable = false)
    private String codigoLote;

    @Column(nullable = false)
    private LocalDateTime fechaRecepcion;

    @Column(nullable = false, length = 300)
    private String descripcion;

    @Column(name = "hash_blockchain", nullable = false, updatable = false)
    private String hashBlockchain;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "RECIBIDO";
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distribuidor", nullable = false)
    private Usuario distribuidor;

    @ManyToMany
    @JoinTable(
            name = "distribucion_empaques",
            joinColumns = @JoinColumn(name = "id_distribucion"),
            inverseJoinColumns = @JoinColumn(name = "id_empaque")
    )
    @JsonIgnoreProperties({"distribuciones", "lote", "transportes"})
    private List<Empaque> empaques = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transporte")
    @JsonIgnoreProperties({"empaques", "cargas"})
    private Transporte transporte;

    public Long getIdDistribucion() {
        return idDistribucion;
    }

    public void setIdDistribucion(Long idDistribucion) {
        this.idDistribucion = idDistribucion;
    }

    public List<Empaque> getEmpaques() {
        return empaques;
    }

    public void setEmpaques(List<Empaque> empaques) {
        this.empaques = empaques;
    }

    public Usuario getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Usuario distribuidor) {
        this.distribuidor = distribuidor;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
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

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDateTime fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }
}
