// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.20;

import "./ILoteContract.sol";

contract TransporteContract {
    address public admin;
    ILoteContract public loteContract;

    mapping(address => bool) public isTransportista;
    modifier onlyAdmin() {
        require(msg.sender == admin, "Solo ADMIN");
        _;
    }
    modifier onlyTransportista() {
        require(isTransportista[msg.sender], "Requiere rol TRANSPORTISTA");
        _;
    }

    constructor(address _loteContractAddress) {
        admin = msg.sender;
        loteContract = ILoteContract(_loteContractAddress);
    }

    function setTransportista(address account, bool allowed) external onlyAdmin {
        isTransportista[account] = allowed;
    }

    struct Transporte {
        uint256 id;
        string placaVehiculo;
        string lugarSalida;
        string destino;
        uint256 fechaSalida; // timestamp
        string descripcion;
        string hashBlockchain;
        uint256 fechaRegistro;
        address transportista;
        uint256[] empaquesIds; // empaques asociados (many-to-many en JPA)
        bool exists;
    }

    uint256 private nextTransporteId = 1;
    mapping(uint256 => Transporte) public transportes;

    // Cargas (evento intermedio)
    struct Carga {
        uint256 id;
        uint256 transporteId;
        uint256[] empaquesIds;
        uint256 fechaCarga;
        string lugarCarga;
        address transportista;
        bool exists;
    }
    uint256 private nextCargaId = 1;
    mapping(uint256 => Carga) public cargas;

    // EVENTS
    event TransporteRegistrado(uint256 indexed idTransporte, address indexed transportista);
    event CargaCreada(uint256 indexed idCarga, uint256 indexed idTransporte);

    // Registrar transporte (requiere validación de que empaques existen y pertenecen a lotes)
    function registrarTransporte(
        string calldata placaVehiculo,
        string calldata lugarSalida,
        string calldata destino,
        uint256 fechaSalida,
        string calldata descripcion,
        uint256[] calldata empaquesIds
    ) external onlyTransportista returns (uint256) {
        require(bytes(placaVehiculo).length > 0, "placa requerida");

        // validar con LoteContract que los empaques existen (y opcionalmente que pertenezcan a un lote)
        for (uint i = 0; i < empaquesIds.length; i++) {
            uint256 eid = empaquesIds[i];
            require(loteContract.existsEmpaque(eid), "Empaque no existe en LoteContract");
            // no forzamos que pertenezcan a un lote concreto; la app valida antes.
        }

        uint256 id = nextTransporteId++;
        Transporte storage t = transportes[id];
        t.id = id;
        t.placaVehiculo = placaVehiculo;
        t.lugarSalida = lugarSalida;
        t.destino = destino;
        t.fechaSalida = fechaSalida;
        t.descripcion = descripcion;
        t.hashBlockchain = "";
        t.fechaRegistro = block.timestamp;
        t.transportista = msg.sender;
        t.exists = true;

        for (uint i = 0; i < empaquesIds.length; i++) {
            t.empaquesIds.push(empaquesIds[i]);
        }

        emit TransporteRegistrado(id, msg.sender);
        return id;
    }

    // Crear carga (evento de escaneo/registro de carga)
    function crearCarga(uint256 transporteId, uint256[] calldata empaquesIds, uint256 fechaCarga, string calldata lugarCarga)
        external onlyTransportista returns (uint256)
    {
        Transporte storage t = transportes[transporteId];
        require(t.exists, "Transporte no existe");
        require(t.transportista == msg.sender, "No es owner del transporte");

        // Validar que empaques estén dentro del transporte (opcional) o existan
        for (uint i = 0; i < empaquesIds.length; i++) {
            uint256 eid = empaquesIds[i];
            require(loteContract.existsEmpaque(eid), "Empaque no existe segun LoteContract");
            // marca o checks adicionales pueden realizarse en LoteContract vía calls si lo deseas
        }

        uint256 id = nextCargaId++;
        Carga storage c = cargas[id];
        c.id = id;
        c.transporteId = transporteId;
        c.fechaCarga = fechaCarga;
        c.lugarCarga = lugarCarga;
        c.transportista = msg.sender;
        c.exists = true;
        for (uint i = 0; i < empaquesIds.length; i++) {
            c.empaquesIds.push(empaquesIds[i]);
        }

        emit CargaCreada(id, transporteId);
        return id;
    }

    // Funciones de utilidad para DistribucionContract
    function existsTransporte(uint256 transporteId) external view returns (bool) {
        return transportes[transporteId].exists;
    }

    function getTransporteEmpaques(uint256 transporteId) external view returns (uint256[] memory) {
        require(transportes[transporteId].exists, "Transporte no existe");
        return transportes[transporteId].empaquesIds;
    }

    // setear hash de blockchain
    function setTransporteHash(uint256 transporteId, string calldata hash) external {
        Transporte storage t = transportes[transporteId];
        require(t.exists, "Transporte no existe");
        require(msg.sender == t.transportista || msg.sender == admin, "No autorizado");
        t.hashBlockchain = hash;
    }
}
