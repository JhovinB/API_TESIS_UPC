// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract LoteContract {

    // --- Estructuras ---
    struct Empaque {
        uint256 id;
        string codigoQR;
        uint256 peso; // gramos
        string unidadMedida;
        uint256 loteId; // referencia al lote
        address creador;
        bool exists;
    }

    struct Lote {
        uint256 id;
        string codigoLote;
        string especie;
        string fechaCosecha;
        bool escanearQR;
        uint256 fechaRegistro;
        Empaque[] empaques;
        bool exists;
    }

    // --- Mapeos ---
    mapping(uint256 => Empaque) public empaques; // id => empaque
    mapping(uint256 => Lote) public lotes;       // id => lote
    mapping(string => uint256) public loteIdByCodigo;
    mapping(string => uint256) public empaqueIdByCodigo;

    uint256 public nextLoteId = 1;
    uint256 public nextEmpaqueId = 1;

    string[] private codigosLotes;

    // --- Eventos ---
    event LoteRegistrado(
        uint256 indexed loteId,
        string codigoLote,
        address registradoPor,
        uint256 fechaRegistro
    );

    event EmpaqueCreado(
        uint256 indexed empaqueId,
        string codigoQR,
        uint256 loteId,
        address registradoPor
    );

    // --- Registro de lote con empaques ---
    function registrarLote(
        string memory _codigoLote,
        string memory _especie,
        string memory _fechaCosecha,
        bool _escanearQR,
        string[] memory _codigosQR,
        uint256[] memory _pesos,
        string[] memory _unidades
    ) public returns (uint256) {
        require(loteIdByCodigo[_codigoLote] == 0, "Lote ya registrado");
        require(
            _codigosQR.length == _pesos.length && _pesos.length == _unidades.length,
            "Datos de empaques inconsistentes"
        );
        require(_codigosQR.length > 0, "Debe incluir al menos un empaque");

        uint256 loteId = nextLoteId++;
        Lote storage nuevoLote = lotes[loteId];
        nuevoLote.id = loteId;
        nuevoLote.codigoLote = _codigoLote;
        nuevoLote.especie = _especie;
        nuevoLote.fechaCosecha = _fechaCosecha;
        nuevoLote.escanearQR = _escanearQR;
        nuevoLote.fechaRegistro = block.timestamp;
        nuevoLote.exists = true;

        for (uint i = 0; i < _codigosQR.length; i++) {
            require(empaqueIdByCodigo[_codigosQR[i]] == 0, "codigoQR ya registrado");

            uint256 empaqueId = nextEmpaqueId++;
            Empaque storage e = empaques[empaqueId];
            e.id = empaqueId;
            e.codigoQR = _codigosQR[i];
            e.peso = _pesos[i];
            e.unidadMedida = _unidades[i];
            e.loteId = loteId;
            e.creador = msg.sender;
            e.exists = true;

            empaqueIdByCodigo[_codigosQR[i]] = empaqueId;
            nuevoLote.empaques.push(e);

            emit EmpaqueCreado(empaqueId, _codigosQR[i], loteId, msg.sender);
        }

        loteIdByCodigo[_codigoLote] = loteId;
        codigosLotes.push(_codigoLote);

        emit LoteRegistrado(loteId, _codigoLote, msg.sender, block.timestamp);

        return loteId;
    }

    // --- Función requerida para TransporteContract ---
    function existsEmpaque(uint256 empaqueId) external view returns (bool) {
        return empaques[empaqueId].exists;
    }

    // --- Consultas ---
    function obtenerLote(uint256 loteId) public view returns (
        string memory codigoLote,
        string memory especie,
        string memory fechaCosecha,
        bool escanearQR,
        uint256 fechaRegistro,
        uint cantidadEmpaques
    ) {
        Lote storage lote = lotes[loteId];
        require(lote.exists, "Lote no encontrado");
        return (
            lote.codigoLote,
            lote.especie,
            lote.fechaCosecha,
            lote.escanearQR,
            lote.fechaRegistro,
            lote.empaques.length
        );
    }

    function obtenerEmpaque(uint256 empaqueId) public view returns (
        string memory codigoQR,
        uint256 peso,
        string memory unidadMedida,
        uint256 loteId
    ) {
        Empaque storage e = empaques[empaqueId];
        require(e.exists, "Empaque no existe");
        return (e.codigoQR, e.peso, e.unidadMedida, e.loteId);
    }

    function obtenerCodigosLotes() public view returns (string[] memory) {
        return codigosLotes;
    }
}
