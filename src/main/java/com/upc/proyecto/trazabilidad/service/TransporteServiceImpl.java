package com.upc.proyecto.trazabilidad.service;


import com.upc.proyecto.trazabilidad.blockchain.BlockchainService;
import com.upc.proyecto.trazabilidad.dto.TransporteRequest;
import com.upc.proyecto.trazabilidad.dto.TransporteResponse;
import com.upc.proyecto.trazabilidad.dto.TransporteResumenResponse;
import com.upc.proyecto.trazabilidad.entity.CargaTransporte;
import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.entity.Transporte;
import com.upc.proyecto.trazabilidad.entity.Usuario;
import com.upc.proyecto.trazabilidad.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransporteServiceImpl implements TransporteService{

    @Autowired
    private TransporteRepository transporteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmpaqueRepository empaqueRepository;
    @Autowired
    private CargaTransporteRepository cargaTransporteRepository;

    @Autowired
    private BlockchainService blockchainService;

    @Override
    public List<TransporteResponse> listarTransportesCompleto() {
        List<Transporte> transportes = transporteRepository.findAll();

        return transportes.stream()
                .map(this::convertirAResponse)
                .toList();
    }
    private TransporteResponse convertirAResponse(Transporte transporte) {
        TransporteResponse response = new TransporteResponse();

        response.setIdTransporte(transporte.getIdTransporte());
        response.setPlacaVehiculo(transporte.getPlacaVehiculo());
        response.setLugarSalida(transporte.getLugarSalida());
        response.setDestino(transporte.getDestino());
        response.setFechaSalida(transporte.getFechaSalida());
        response.setDescripcion(transporte.getDescripcion());
        response.setHashBlockchain(transporte.getHashBlockchain());
        response.setFechaRegistro(transporte.getFechaRegistro());

        // Nombre del transportista
        if (transporte.getTransportista() != null) {
            response.setNombreTransportista(transporte.getTransportista().getNombres());
        }

        // Códigos QR asociados
        if (transporte.getCargas() != null && !transporte.getCargas().isEmpty()) {
            List<String> codigosQR = transporte.getCargas().stream()
                    .map(carga -> carga.getEmpaque().getCodigoQR())
                    .filter(Objects::nonNull)
                    .toList();
            response.setCodigosQR(codigosQR);
        }

        return response;
    }

    @Override
    public TransporteResponse registrarTransporte(TransporteRequest request, String correoTransportista) throws Exception {
        Usuario transportista = usuarioRepository.findByCorreo(correoTransportista)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado."));

        boolean esTransportista = transportista.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("TRANSPORTISTA")|| rol.getNombre().equalsIgnoreCase("ADMIN"));
        if (!esTransportista) {
            throw new RuntimeException("El usuario no tiene permisos para registrar transporte.");
        }

        List<Empaque> empaques = new ArrayList<>();
        for (String codigoQR : request.getCodigosQR()) {
            Empaque empaque = empaqueRepository.findByCodigoQR(codigoQR)
                    .orElseThrow(() -> new RuntimeException("Empaque con código " + codigoQR + " no encontrado."));
            if (cargaTransporteRepository.existsByEmpaqueIdEmpaque(empaque.getIdEmpaque())) {
                throw new RuntimeException("Empaque con código " + codigoQR + " ya fue transportado.");
            }
            empaques.add(empaque);
        }

        // Obtener el hash del lote desde el primer empaque
        String hashBlockchainTransporte = empaques.get(0).getLote().getHashBlockchain();

        Transporte transporte = new Transporte();
        transporte.setPlacaVehiculo(request.getPlacaVehiculo());
        transporte.setLugarSalida(request.getLugarSalida());
        transporte.setDestino(request.getDestino());
        transporte.setFechaSalida(request.getFechaSalida());
        transporte.setDescripcion(request.getDescripcion());
        transporte.setTransportista(transportista);
        transporte.setHashBlockchain(hashBlockchainTransporte);
        //transporte.setHashBlockchain("HASH-" + UUID.randomUUID());
        //String hashBlockchain = blockchainService.registrarTransporte(request, empaques);
        //transporte.setHashBlockchain(hashBlockchain);
        transporte.setFechaRegistro(LocalDateTime.now());

        List<CargaTransporte> cargas = new ArrayList<>();
        for (Empaque e : empaques) {
            CargaTransporte carga = new CargaTransporte();
            carga.setEmpaque(e);
            carga.setTransporte(transporte);
            cargas.add(carga);
        }
        transporte.setCargas(cargas);

        Transporte guardado = transporteRepository.save(transporte);
        return convertirAResponse(guardado);
    }
    @Override
    public List<TransporteResumenResponse> listarTransportes() {
        List<Transporte> transportes = transporteRepository.findAll();

        return transportes.stream()
                .map(t -> new TransporteResumenResponse(
                        t.getIdTransporte(),
                        t.getPlacaVehiculo(),
                        t.getLugarSalida(),
                        t.getDestino(),
                        t.getFechaSalida()
                ))
                .toList();
    }
    @Override
    public TransporteResponse obtenerPorId(Long id) {
        Transporte transporte = transporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transporte no encontrado con ID: " + id));
        return convertirAResponse(transporte);
    }
}

