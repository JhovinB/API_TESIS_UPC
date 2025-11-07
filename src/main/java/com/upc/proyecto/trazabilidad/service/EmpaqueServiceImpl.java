package com.upc.proyecto.trazabilidad.service;

import com.upc.proyecto.trazabilidad.entity.Distribucion;
import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.entity.Lote;
import com.upc.proyecto.trazabilidad.entity.Transporte;
import com.upc.proyecto.trazabilidad.repository.DistribucionRepository;
import com.upc.proyecto.trazabilidad.repository.EmpaqueRepository;
import com.upc.proyecto.trazabilidad.repository.LoteRepository;
import com.upc.proyecto.trazabilidad.repository.TransporteRepository;
import com.upc.proyecto.trazabilidad.util.QrGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpaqueServiceImpl implements EmpaqueService{

    @Autowired
    private EmpaqueRepository empaqueRepository;
    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private DistribucionRepository distribucionRepository;

    @Override
    public List<Empaque> listarEmpaques() {
        return empaqueRepository.findAll();
    }

    @Override
    public Optional<Empaque> obtenerEmpaquePorId(Long id) {
        return empaqueRepository.findById(id);
    }
    @Override
    public Optional<Empaque> obtenerPorCodigoQR(String codigoQR) {
        return empaqueRepository.findByCodigoQR(codigoQR.trim().toUpperCase());
    }
    @Override
    public void eliminarEmpaque(Long id) {
        empaqueRepository.deleteById(id);
    }
    @Override
    public Optional<Empaque> obtenerTrazabilidadCompletaPorCodigoQR(String codigoQR) {
        // Buscar empaque por código QR
        Optional<Empaque> empaqueOpt = empaqueRepository.findByCodigoQR(codigoQR.trim());
        if (empaqueOpt.isEmpty()) {
            return Optional.empty();
        }

        Empaque empaque = empaqueOpt.get();

        // Cargar Lote completo si existe
        if (empaque.getLote() != null && empaque.getLote().getIdLote() != null) {
            loteRepository.findById(empaque.getLote().getIdLote())
                    .ifPresent(empaque::setLote);
        }

        // Cargar Transportes asociados (ManyToMany)
        List<Transporte> transportes = transporteRepository.findByEmpaques_IdEmpaque(empaque.getIdEmpaque());
        empaque.setTransportes(transportes);

        // Cargar Distribuciones asociadas (ManyToMany)
        List<Distribucion> distribuciones = distribucionRepository.findByEmpaques_IdEmpaque(empaque.getIdEmpaque());

        // Filtrar distribuciones válidas por estado
        distribuciones = distribuciones.stream()
                .filter(d -> "RECIBIDO".equalsIgnoreCase(d.getEstado()) || "CERRADO".equalsIgnoreCase(d.getEstado()))
                .toList();

        if (distribuciones.isEmpty()) {
            throw new IllegalStateException("El empaque aún se encuentra en proceso de trazabilidad o no ha sido recepcionado.");
        }

        // Asociar distribuciones al empaque
        empaque.setDistribuciones(distribuciones);

        return Optional.of(empaque);
    }

}
