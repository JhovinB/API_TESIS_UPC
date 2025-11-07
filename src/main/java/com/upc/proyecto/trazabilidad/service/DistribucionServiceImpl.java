package com.upc.proyecto.trazabilidad.service;

import com.upc.proyecto.trazabilidad.blockchain.BlockchainService;
import com.upc.proyecto.trazabilidad.dto.DistribucionRequest;
import com.upc.proyecto.trazabilidad.dto.DistribucionResponse;
import com.upc.proyecto.trazabilidad.dto.DistribucionResumenResponse;
import com.upc.proyecto.trazabilidad.dto.TransporteResumenResponse;
import com.upc.proyecto.trazabilidad.entity.*;
import com.upc.proyecto.trazabilidad.repository.CargaTransporteRepository;
import com.upc.proyecto.trazabilidad.repository.DistribucionRepository;
import com.upc.proyecto.trazabilidad.repository.EmpaqueRepository;
import com.upc.proyecto.trazabilidad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DistribucionServiceImpl implements DistribucionService {

    @Autowired
    private DistribucionRepository distribucionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpaqueRepository empaqueRepository;

    @Autowired
    private CargaTransporteRepository cargaTransporteRepository;

    @Override
    public List<Distribucion> listarDistribuciones() {
        return distribucionRepository.findAll();
    }

    @Override
    public Distribucion obtenerPorId(Long id) {
        return distribucionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Distribución no encontrada con ID: " + id));
    }
    @Override
    public List<DistribucionResumenResponse> listarDistribucionResumen() {
        List<Distribucion> distribuciones = distribucionRepository.findAll();

        return distribuciones.stream()
                .map(d -> new DistribucionResumenResponse(
                        d.getIdDistribucion(),
                        d.getCodigoLote(),
                        d.getEstado(),
                        d.getFechaRecepcion()
                ))
                .toList();
    }

    @Override
    public List<Distribucion> listarDistribucionesPorCorreo(String correoDistribuidor) {
        Usuario distribuidor = usuarioRepository.findByCorreo(correoDistribuidor)
                .orElseThrow(() -> new RuntimeException("Distribuidor no encontrado."));

        boolean esDistribuidor = distribuidor.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("DISTRIBUIDOR")
                        || rol.getNombre().equalsIgnoreCase("ADMIN"));
        if (!esDistribuidor) {
            throw new RuntimeException("El usuario no tiene permisos para listar sus recepciones.");
        }
        return distribucionRepository.findByDistribuidorIdUsuario(distribuidor.getIdUsuario());
    }
    private DistribucionResponse convertirAResponse(Distribucion distribucion) {
        DistribucionResponse response = new DistribucionResponse();

        response.setIdDistribucion(distribucion.getIdDistribucion());
        response.setCodigoLote(distribucion.getCodigoLote());
        response.setDescripcion(distribucion.getDescripcion());
        response.setFechaRecepcion(distribucion.getFechaRecepcion());
        response.setEstado(distribucion.getEstado());
        response.setHashBlockchain(distribucion.getHashBlockchain());
        response.setFechaRegistro(distribucion.getFechaRegistro());

        if (distribucion.getDistribuidor() != null) {
            Usuario distribuidor = distribucion.getDistribuidor();
            response.setDistribuidor(distribuidor);
            String nombreCompleto = distribuidor.getNombres() + " " + distribuidor.getApellidos();
            response.setNombreDistribuidor(nombreCompleto.trim());
        }

        if (distribucion.getEmpaques() != null && !distribucion.getEmpaques().isEmpty()) {
            response.setEmpaques(distribucion.getEmpaques());

            List<String> codigosQR = distribucion.getEmpaques().stream()
                    .map(Empaque::getCodigoQR)
                    .toList();
            response.setCodigosQR(codigosQR);
        }

        if (distribucion.getEmpaques() != null && !distribucion.getEmpaques().isEmpty()) {
            Empaque primerEmpaque = distribucion.getEmpaques().get(0);

            Optional<CargaTransporte> cargaOpt = cargaTransporteRepository
                    .findFirstByEmpaqueIdEmpaque(primerEmpaque.getIdEmpaque());

            if (cargaOpt.isPresent()) {
                Transporte transporte = cargaOpt.get().getTransporte();
                response.setTransporte(transporte);
            }
        }

        return response;
    }
    @Override
    public DistribucionResponse registrarRecepcion(DistribucionRequest request, String correoDistribuidor) {

        Usuario distribuidor = usuarioRepository.findByCorreo(correoDistribuidor)
                .orElseThrow(() -> new RuntimeException("Distribuidor no encontrado."));

        boolean esDistribuidor = distribuidor.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("DISTRIBUIDOR")
                        || rol.getNombre().equalsIgnoreCase("ADMIN"));

        if (!esDistribuidor) {
            throw new RuntimeException("El usuario no tiene permisos para registrar una recepción.");
        }

        if (request.getCodigosQR() == null || request.getCodigosQR().isEmpty()) {
            throw new RuntimeException("Debe proporcionar al menos un código QR de empaque.");
        }

        List<Empaque> empaques = new ArrayList<>();
        for (String codigoQR : request.getCodigosQR()) {
            Empaque empaque = empaqueRepository.findByCodigoQR(codigoQR)
                    .orElseThrow(() -> new RuntimeException("Empaque con código " + codigoQR + " no encontrado."));

            boolean fueTransportado = cargaTransporteRepository.existsByEmpaqueIdEmpaque(empaque.getIdEmpaque());
            if (!fueTransportado) {
                throw new RuntimeException("El empaque con código " + codigoQR + " no ha sido transportado aún.");
            }

            boolean empaqueYaRecepcionado = distribucionRepository.existsByEmpaques_CodigoQR(codigoQR);
            if (empaqueYaRecepcionado) {
                throw new RuntimeException("El empaque con código " + codigoQR + " ya fue recepcionado anteriormente.");
            }

            empaques.add(empaque);
        }

        String codigoLote = empaques.get(0).getLote().getCodigoLote();
        boolean mismoLote = empaques.stream()
                .allMatch(e -> e.getLote().getCodigoLote().equals(codigoLote));
        if (!mismoLote) {
            throw new RuntimeException("Todos los empaques deben pertenecer al mismo lote.");
        }

        // Obtener transporte asociado
        Transporte transporte = cargaTransporteRepository
                .findFirstByEmpaqueIdEmpaque(empaques.get(0).getIdEmpaque())
                .map(CargaTransporte::getTransporte)
                .orElseThrow(() -> new RuntimeException("No se encontró el transporte asociado al empaque."));

        String hashBlockchainDistribucion = empaques.get(0).getLote().getHashBlockchain();

        Distribucion distribucion = new Distribucion();
        distribucion.setCodigoLote(codigoLote);
        distribucion.setDescripcion(request.getDescripcion());
        distribucion.setFechaRecepcion(LocalDateTime.now());
        distribucion.setDistribuidor(distribuidor);
        distribucion.setEmpaques(empaques);
        distribucion.setTransporte(transporte);
        distribucion.setHashBlockchain(hashBlockchainDistribucion);
        distribucion.setEstado("RECIBIDO");
        distribucion.setFechaRegistro(LocalDateTime.now());

        Distribucion guardado = distribucionRepository.save(distribucion);

        Distribucion distribucionCompleta = distribucionRepository.findById(guardado.getIdDistribucion())
                .orElseThrow(() -> new RuntimeException("Error al recargar la distribución guardada."));

        return convertirAResponse(distribucionCompleta);
    }


}
