package com.upc.proyecto.trazabilidad.service;

import com.upc.proyecto.trazabilidad.blockchain.BlockchainService;
import com.upc.proyecto.trazabilidad.dto.EmpaqueRequest;
import com.upc.proyecto.trazabilidad.dto.LoteRequest;
import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.entity.Lote;
import com.upc.proyecto.trazabilidad.entity.Usuario;
import com.upc.proyecto.trazabilidad.repository.EmpaqueRepository;
import com.upc.proyecto.trazabilidad.repository.LoteRepository;
import com.upc.proyecto.trazabilidad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LoteServiceImpl implements LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private EmpaqueRepository empaqueRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BlockchainService blockchainService;

    /*@Override
    public Lote guardarLoteDesdeRequest(LoteRequest request, String correoProductor) {
        // Buscar al usuario por correo desde el JWT
        Usuario productor = usuarioRepository.findByCorreo(correoProductor)
                .orElseThrow(() -> new RuntimeException("El productor no existe."));

        // Verificar que sea PRODUCTOR
        boolean esProductor = productor.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("PRODUCTOR"));

        if (!esProductor) {
            throw new RuntimeException("El usuario no tiene permisos para registrar lotes.");
        }

        // Validar que no se repita el código del lote
        if (loteRepository.findByCodigoLote(request.getCodigoLote()).isPresent()) {
            throw new RuntimeException("Ya existe un lote con código: " + request.getCodigoLote());
        }

        // Validar y construir empaques
        List<Empaque> empaquesAsignados = new ArrayList<>();
        if (request.getEmpaques() == null || request.getEmpaques().isEmpty()) {
            throw new RuntimeException("Debe registrar al menos un empaque.");
        }

        for (EmpaqueRequest empaqueDto : request.getEmpaques()) {
            if (empaqueDto.getCodigoQR() == null || empaqueDto.getCodigoQR().isBlank()) {
                throw new RuntimeException("Código QR requerido para cada empaque.");
            }
            if (empaqueDto.getPeso().compareTo(BigDecimal.ZERO) <= 0){
                throw new RuntimeException("Peso inválido para el empaque con QR: " + empaqueDto.getCodigoQR());
            }
            if (empaqueDto.getUnidadMedida() == null || empaqueDto.getUnidadMedida().isBlank()) {
                throw new RuntimeException("Unidad de medida requerida para el empaque con QR: " + empaqueDto.getCodigoQR());
            }
            if (empaqueRepository.findByCodigoQR(empaqueDto.getCodigoQR()).isPresent()) {
                throw new RuntimeException("Empaque con QR " + empaqueDto.getCodigoQR() + " ya existe.");
            }

            Empaque empaque = new Empaque();
            empaque.setCodigoQR(empaqueDto.getCodigoQR());
            empaque.setPeso(empaqueDto.getPeso());
            empaque.setUnidadMedida(empaqueDto.getUnidadMedida());
            empaquesAsignados.add(empaque);
        }

        // Crear el lote y relacionarlo
        Lote lote = new Lote();
        lote.setCodigoLote(request.getCodigoLote());
        lote.setEspecie(request.getEspecie());
        lote.setFechaCosecha(request.getFechaCosecha());
        lote.setHashBlockchain("hash-" + UUID.randomUUID()); // Simulado
        lote.setProductor(productor);

        for (Empaque e : empaquesAsignados) {
            e.setLote(lote);
        }

        lote.setEmpaques(empaquesAsignados);

        return loteRepository.save(lote);
    }*/

    @Override
    public Lote guardarLote(Lote lote) {
        // Validar que el lote tenga al menos un empaque
        if (lote.getEmpaques() == null || lote.getEmpaques().isEmpty()) {
            throw new RuntimeException("El lote debe contener al menos un empaque.");
        }
        // Validar que el productor exista
        Usuario productor = usuarioRepository.findById(lote.getProductor().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("El productor especificado no existe."));

        // Validar que el usuario tenga el rol PRODUCTOR
        boolean esProductor = productor.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("PRODUCTOR") || rol.getNombre().equalsIgnoreCase("ADMIN"));

        if (!esProductor) {
            throw new RuntimeException("El usuario no tiene permisos para registrar lotes.");
        }

        // Validar que el codigoLote no se repita
        if (loteRepository.findByCodigoLote(lote.getCodigoLote()).isPresent()) {
            throw new RuntimeException("Ya existe un lote con el código: " + lote.getCodigoLote());
        }
        // Validar y asignar empaques
        List<Empaque> empaquesAsignados = new ArrayList<>();
        for (Empaque empaqueEntrada : lote.getEmpaques()) {
            // Validaciones del empaque
            if (empaqueEntrada.getCodigoQR() == null || empaqueEntrada.getCodigoQR().isBlank()) {
                throw new RuntimeException("Cada empaque debe tener un código QR.");
            }
            if (empaqueEntrada.getUnidadMedida() == null || empaqueEntrada.getUnidadMedida().isBlank()) {
                throw new RuntimeException("Cada empaque debe tener una unidad de medida.");
            }
            if (empaqueEntrada.getPeso().compareTo(BigDecimal.ZERO) <= 0){
                throw new RuntimeException("El peso del empaque debe ser mayor a 0.");
            }
            // Validar que ese código QR no haya sido ya usado
            if (empaqueRepository.findByCodigoQR(empaqueEntrada.getCodigoQR()).isPresent()) {
                throw new RuntimeException("Empaque con QR " + empaqueEntrada.getCodigoQR() + " ya está asignado a un lote.");
            }
            empaqueEntrada.setLote(lote);
            empaquesAsignados.add(empaqueEntrada);
        }
        lote.setProductor(productor); // Asegurar que el productor está bien asignado desde BD
        lote.setEmpaques(empaquesAsignados);

        return loteRepository.save(lote); // Guarda lote y relaciona los empaques
    }
    @Override
    public Optional<Lote> obtenerLotePorId(Long id) {
        return loteRepository.findById(id);
    }

    @Override
    public Optional<Lote> obtenerPorCodigo(String codigoLote) {
        return loteRepository.findByCodigoLote(codigoLote);
    }

    @Override
    public List<Lote> listarLotes() {
        return loteRepository.findAll();
    }

    @Override
    public void eliminarLote(Long id) {
        loteRepository.deleteById(id);
    }
   /* @Override
    public Lote guardarLoteDesdeRequest(LoteRequest request, String correoProductor) {

        Usuario productor = usuarioRepository.findByCorreo(correoProductor)
                .orElseThrow(() -> new RuntimeException("El productor no existe."));

        boolean esProductor = productor.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("PRODUCTOR")|| rol.getNombre().equalsIgnoreCase("ADMIN"));

        if (!esProductor) {
            throw new RuntimeException("El usuario no tiene permisos para registrar lotes.");
        }

        if (loteRepository.findByCodigoLote(request.getCodigoLote()).isPresent()) {
            throw new RuntimeException("Ya existe un lote con código: " + request.getCodigoLote());
        }

        // Validar empaques
        List<Empaque> empaquesAsignados = new ArrayList<>();
        for (EmpaqueRequest empaqueDto : request.getEmpaques()) {
            if (empaqueDto.getCodigoQR() == null || empaqueDto.getCodigoQR().isBlank()) {
                throw new RuntimeException("Código QR requerido para cada empaque.");
            }
            if (empaqueDto.getPeso().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Peso inválido para el empaque con QR: " + empaqueDto.getCodigoQR());
            }
            if (empaqueDto.getUnidadMedida() == null || empaqueDto.getUnidadMedida().isBlank()) {
                throw new RuntimeException("Unidad de medida requerida para el empaque con QR: " + empaqueDto.getCodigoQR());
            }
            if (empaqueRepository.findByCodigoQR(empaqueDto.getCodigoQR()).isPresent()) {
                throw new RuntimeException("Empaque con QR " + empaqueDto.getCodigoQR() + " ya existe.");
            }

            Empaque empaque = new Empaque();
            empaque.setCodigoQR(empaqueDto.getCodigoQR());
            empaque.setPeso(empaqueDto.getPeso());
            empaque.setUnidadMedida(empaqueDto.getUnidadMedida());
            empaquesAsignados.add(empaque);
        }

        //REGISTRAR EN BLOCKCHAIN
        // String txHash = "hash-simulado-" + UUID.randomUUID();
        String txHash;
        try {
           // txHash = blockchainService.registrarLote(request);

        } catch (Exception e) {
            throw new RuntimeException("Error al registrar en Blockchain: " + e.getMessage());
        }

        //Crear el lote en BD
        Lote lote = new Lote();
        lote.setCodigoLote(request.getCodigoLote());
        lote.setEspecie(request.getEspecie());
        lote.setFechaCosecha(request.getFechaCosecha());
        lote.setProductor(productor);
       // lote.setHashBlockchain(txHash); //

        for (Empaque e : empaquesAsignados) {
            e.setLote(lote);
        }

        lote.setEmpaques(empaquesAsignados);

        return loteRepository.save(lote);
    }*/
   @Override
   public Lote guardarLoteDesdeRequest(LoteRequest request, String correoProductor) {

       // Validar productor
       Usuario productor = usuarioRepository.findByCorreo(correoProductor)
               .orElseThrow(() -> new RuntimeException("El productor no existe."));

       boolean esProductor = productor.getRoles().stream()
               .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("PRODUCTOR")
                       || rol.getNombre().equalsIgnoreCase("ADMIN"));

       if (!esProductor) {
           throw new RuntimeException("El usuario no tiene permisos para registrar lotes.");
       }

       // Validar duplicados
       if (loteRepository.findByCodigoLote(request.getCodigoLote()).isPresent()) {
           throw new RuntimeException("Ya existe un lote con código: " + request.getCodigoLote());
       }

       //  Validar empaques
       List<Empaque> empaquesAsignados = new ArrayList<>();
       for (EmpaqueRequest empaqueDto : request.getEmpaques()) {
           if (empaqueDto.getCodigoQR() == null || empaqueDto.getCodigoQR().isBlank()) {
               throw new RuntimeException("Código QR requerido para cada empaque.");
           }
           if (empaqueDto.getPeso().compareTo(BigDecimal.ZERO) <= 0) {
               throw new RuntimeException("Peso inválido para el empaque con QR: " + empaqueDto.getCodigoQR());
           }
           if (empaqueDto.getUnidadMedida() == null || empaqueDto.getUnidadMedida().isBlank()) {
               throw new RuntimeException("Unidad de medida requerida para el empaque con QR: " + empaqueDto.getCodigoQR());
           }
           if (empaqueRepository.findByCodigoQR(empaqueDto.getCodigoQR()).isPresent()) {
               throw new RuntimeException("Empaque con QR " + empaqueDto.getCodigoQR() + " ya existe.");
           }

           Empaque empaque = new Empaque();
           empaque.setCodigoQR(empaqueDto.getCodigoQR());
           empaque.setPeso(empaqueDto.getPeso());
           empaque.setUnidadMedida(empaqueDto.getUnidadMedida());
           empaquesAsignados.add(empaque);
       }

       // Registrar en Blockchain
       String txHash;
       try {
           System.out.println("➡Registrando lote en Blockchain (adaptado)...");
           txHash = blockchainService.registrarLote(request);
           System.out.println("Transacción Blockchain confirmada: " + txHash);
       } catch (Exception e) {
           throw new RuntimeException("Error al registrar lote en Blockchain: " + e.getMessage(), e);
       }

       //Crear lote en BD
       Lote lote = new Lote();
       lote.setCodigoLote(request.getCodigoLote());
       lote.setEspecie(request.getEspecie());
       lote.setFechaCosecha(request.getFechaCosecha());
       lote.setProductor(productor);
       lote.setHashBlockchain(txHash);

       for (Empaque e : empaquesAsignados) {
           e.setLote(lote);
       }

       lote.setEmpaques(empaquesAsignados);

       Lote loteGuardado = loteRepository.save(lote);

       // Guardar empaques en BD
       empaqueRepository.saveAll(empaquesAsignados);

       System.out.println("📦 Lote guardado correctamente con ID: " + loteGuardado.getIdLote());
       return loteGuardado;
   }




}
