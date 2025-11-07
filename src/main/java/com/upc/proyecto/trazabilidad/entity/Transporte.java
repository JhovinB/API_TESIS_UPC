package com.upc.proyecto.trazabilidad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transportes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransporte;

    @Column(nullable = false)
    private String placaVehiculo;

    @Column(nullable = false)
    private String lugarSalida;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDateTime fechaSalida;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "hash_blockchain", nullable = false, updatable = false)
    private String hashBlockchain;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transportista_id", nullable = false)
    @JsonIgnore
    private Usuario transportista;

    // CargaTransportes relacionados
    @OneToMany(mappedBy = "transporte", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CargaTransporte> cargas = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "carga_transporte",
            joinColumns = @JoinColumn(name = "id_transporte"),
            inverseJoinColumns = @JoinColumn(name = "id_empaque")
    )
    @JsonIgnoreProperties({"transportes", "lote", "distribuciones"})
    private List<Empaque> empaques = new ArrayList<>();

    public Long getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(Long idTransporte) {
        this.idTransporte = idTransporte;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public List<CargaTransporte> getCargas() {
        return cargas;
    }

    public void setCargas(List<CargaTransporte> cargas) {
        this.cargas = cargas;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
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

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getTransportista() {
        return transportista;
    }

    public void setTransportista(Usuario transportista) {
        this.transportista = transportista;
    }

    public List<Empaque> getEmpaques() {
        return empaques;
    }

    public void setEmpaques(List<Empaque> empaques) {
        this.empaques = empaques;
    }
}
