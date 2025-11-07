package com.upc.proyecto.trazabilidad.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "lotes")
@NoArgsConstructor
@AllArgsConstructor
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLote;

    @Column(nullable = false, unique = true)
    private String codigoLote;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private LocalDate fechaCosecha;

    @Column
    private boolean escanearQR;

    @Column
    private String hashBlockchain;

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"lote", "transportes", "distribuciones"})
    private List<Empaque> empaques; // Lista de cajas escaneadas

    @ManyToOne
    @JoinColumn(name = "productor_id", nullable = false)
    private Usuario productor;

    public Long getIdLote() {
        return idLote;
    }

    public void setIdLote(Long idLote) {
        this.idLote = idLote;
    }

    public Usuario getProductor() {
        return productor;
    }

    public void setProductor(Usuario productor) {
        this.productor = productor;
    }

    public List<Empaque> getEmpaques() {
        return empaques;
    }

    public void setEmpaques(List<Empaque> empaques) {
        this.empaques = empaques;
    }

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

    public boolean isEscanearQR() {
        return escanearQR;
    }

    public void setEscanearQR(boolean escanearQR) {
        this.escanearQR = escanearQR;
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

}
