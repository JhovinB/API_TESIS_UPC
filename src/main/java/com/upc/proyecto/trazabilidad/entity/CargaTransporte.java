package com.upc.proyecto.trazabilidad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carga_transporte")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargaTransporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarga;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_transporte", nullable = false)
    @JsonIgnore
    private Transporte transporte;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empaque", nullable = false)
    private Empaque empaque;

    public Empaque getEmpaque() {
        return empaque;
    }

    public void setEmpaque(Empaque empaque) {
        this.empaque = empaque;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public Long getIdCarga() {
        return idCarga;
    }

    public void setIdCarga(Long idCarga) {
        this.idCarga = idCarga;
    }
}
