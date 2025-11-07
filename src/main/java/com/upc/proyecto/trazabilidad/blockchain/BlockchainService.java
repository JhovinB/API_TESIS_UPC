package com.upc.proyecto.trazabilidad.blockchain;

import com.upc.proyecto.trazabilidad.contract.LoteContract;
import com.upc.proyecto.trazabilidad.contract.TransporteContract;
import com.upc.proyecto.trazabilidad.dto.EmpaqueRequest;
import com.upc.proyecto.trazabilidad.dto.LoteRequest;
import com.upc.proyecto.trazabilidad.dto.TransporteRequest;
import com.upc.proyecto.trazabilidad.entity.Empaque;
import com.upc.proyecto.trazabilidad.repository.EmpaqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.*;

@Service
public class BlockchainService {
    private final Web3j web3j;
    private final Credentials credentials;
    private final LoteContract loteContract;
    //private final String contractAddress;
    private final String loteContractAddress;
    //private final TransporteContract transporteContract;

    @Autowired
    private EmpaqueRepository empaqueRepository;
   public BlockchainService() {
       String rpcUrl = "http://localhost:8545";
       String privateKey = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80";

       this.loteContractAddress = "0x5FbDB2315678afecb367f032d93F642f64180aa3";

       this.web3j = Web3j.build(new HttpService(rpcUrl));

       // Crear credenciales
       this.credentials = Credentials.create(privateKey);
       //this.credentials = Credentials.create(privateKey);

       ContractGasProvider gasProvider = new StaticGasProvider(
               BigInteger.valueOf(1_000_000_000L),
               BigInteger.valueOf(6_000_000L)
       );

       this.loteContract = LoteContract.load(loteContractAddress, web3j, credentials, gasProvider);
      // this.transporteContract = TransporteContract.load(transporteContractAddress, web3j, credentials, gasProvider);

   }
    /*// REGISTRO DE TRANSPORTE
    public String registrarTransporteBlockchain(TransporteRequest request, List<Empaque> empaques) throws Exception {

        // Convertir la fecha a timestamp (segundos desde epoch)
        long fechaSalidaTimestamp = request.getFechaSalida().atZone(ZoneId.systemDefault()).toEpochSecond();

        // Convertir los empaques a IDs (BigInteger)
        List<BigInteger> empaquesIds = empaques.stream()
                .map(e -> BigInteger.valueOf(e.getIdEmpaque()))
                .toList();

        System.out.println("Enviando registro de transporte a Blockchain:");
        System.out.println("Placa: " + request.getPlacaVehiculo());
        System.out.println("Lugar salida: " + request.getLugarSalida());
        System.out.println("Destino: " + request.getDestino());
        System.out.println("Fecha salida: " + fechaSalidaTimestamp);
        System.out.println("Empaques IDs: " + empaquesIds);

        // Ejecutar la transacción
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<TransactionReceipt> future = executor.submit(() ->
                transporteContract.registrarTransporte(
                        request.getPlacaVehiculo(),
                        request.getLugarSalida(),
                        request.getDestino(),
                        BigInteger.valueOf(fechaSalidaTimestamp),
                        request.getDescripcion(),
                        empaquesIds
                ).send()
        );

        TransactionReceipt tx = future.get(60, TimeUnit.SECONDS);
        executor.shutdown();

        if (!tx.isStatusOK()) {
            throw new RuntimeException("Transacción de transporte fallida. Status: " + tx.getStatus());
        }

        System.out.println("Transacción confirmada: " + tx.getTransactionHash());
        return tx.getTransactionHash();
    }*/

    public String registrarLote(LoteRequest dto) throws Exception {
        try {
            // Validar que el contrato esté desplegado
            EthGetCode codeResponse = web3j.ethGetCode(loteContractAddress, DefaultBlockParameterName.LATEST).send();
            if (codeResponse.getCode() == null || codeResponse.getCode().equals("0x")) {
                throw new RuntimeException("El contrato no está desplegado en la dirección: " + loteContractAddress);
            }

            System.out.println("📦 Preparando datos para registrar lote en Blockchain");

            // Convertir lista de códigos QR y pesos
            List<String> codigosQR = dto.getEmpaques().stream()
                    .map(EmpaqueRequest::getCodigoQR)
                    .toList();

            List<BigInteger> pesos = dto.getEmpaques().stream()
                    .map(e -> e.getPeso().multiply(BigDecimal.valueOf(1000)).toBigInteger()) // kg → g
                    .toList();

            List<String> unidades = dto.getEmpaques().stream()
                    .map(EmpaqueRequest::getUnidadMedida)
                    .toList();

            //  Convertir la fecha
            String fechaFormateada = dto.getFechaCosecha().toString();

            // 4Log de depuración
            System.out.println("Enviando transacción adaptada:");
            System.out.println("Código Lote: " + dto.getCodigoLote());
            System.out.println("Especie: " + dto.getEspecie());
            System.out.println("Fecha cosecha: " + fechaFormateada);
            System.out.println("Escanear QR: " + dto.isEscanearQR());
            System.out.println("Códigos QR: " + codigosQR);
            System.out.println("Pesos (g): " + pesos);
            System.out.println("Unidades: " + unidades);

            // Ejecutar transacción adaptada al contrato actualizado
            TransactionReceipt txReceipt = loteContract.registrarLote(
                    dto.getCodigoLote(),
                    dto.getEspecie(),
                    fechaFormateada,
                    dto.isEscanearQR(),
                    codigosQR,
                    pesos,
                    unidades
            ).send();

            //  Verificar estado
            if (!txReceipt.isStatusOK()) {
                throw new RuntimeException("Transacción fallida. Status: " + txReceipt.getStatus());
            }

            // 8Obtener evento emitido (si existe)
            List<LoteContract.LoteRegistradoEventResponse> eventos = loteContract.getLoteRegistradoEvents(txReceipt);
            String codigoLoteOnChain = eventos.isEmpty() ? dto.getCodigoLote() : eventos.get(0).codigoLote;

            System.out.println("Lote registrado correctamente en Blockchain");
            System.out.println("Transaction Hash: " + txReceipt.getTransactionHash());
            System.out.println("Código Lote (on-chain): " + codigoLoteOnChain);

            // Devolver hash de transacción (para guardar en BD)
            return txReceipt.getTransactionHash();

        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("timeout")) {
                throw new RuntimeException("Timeout: la transacción en Blockchain tomó demasiado tiempo.");
            }
            throw new RuntimeException("Error al registrar lote en Blockchain (adaptado): " + e.getMessage(), e);
        }
    }

}



