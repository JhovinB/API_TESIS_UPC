// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.20;

interface ILoteContract {
    function existsEmpaque(uint256 empaqueId) external view returns (bool);
}
