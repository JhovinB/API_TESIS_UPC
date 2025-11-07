package com.upc.proyecto.trazabilidad.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.13.0.
 */
@SuppressWarnings("rawtypes")
public class TransporteContract extends Contract {
    public static final String BINARY = "6080604052600160035560016005553480156018575f5ffd5b506040516114d93803806114d98339810160408190526035916066565b5f8054336001600160a01b031991821617909155600180549091166001600160a01b03929092169190911790556091565b5f602082840312156075575f5ffd5b81516001600160a01b0381168114608a575f5ffd5b9392505050565b61143b8061009e5f395ff3fe608060405234801561000f575f5ffd5b50600436106100a6575f3560e01c806380e54e361161006e57806380e54e361461016f5780638713785614610182578063999c7d7f146101a2578063c6db035e146101b5578063f29f699e146101da578063f851a440146101ff575f5ffd5b80633564c8dc146100aa5780633caefeb1146100bf57806343fa78f8146100f157806360f592d81461011c57806365538ea81461013d575b5f5ffd5b6100bd6100b8366004610d7a565b610211565b005b6100d26100cd366004610dc2565b6102c1565b6040516100e89a99989796959493929190610e07565b60405180910390f35b600154610104906001600160a01b031681565b6040516001600160a01b0390911681526020016100e8565b61012f61012a366004610ee6565b6105ba565b6040519081526020016100e8565b61015f61014b366004610f83565b60026020525f908152604090205460ff1681565b60405190151581526020016100e8565b6100bd61017d366004610fb3565b610888565b610195610190366004610dc2565b6108f8565b6040516100e89190610fe8565b61012f6101b036600461102a565b61098b565b61015f6101c3366004610dc2565b5f908152600460205260409020600a015460ff1690565b6101ed6101e8366004610dc2565b610c69565b6040516100e896959493929190611133565b5f54610104906001600160a01b031681565b5f838152600460205260409020600a81015460ff1661024b5760405162461bcd60e51b81526004016102429061117a565b60405180910390fd5b60088101546001600160a01b031633148061026f57505f546001600160a01b031633145b6102ab5760405162461bcd60e51b815260206004820152600d60248201526c4e6f206175746f72697a61646f60981b6044820152606401610242565b600681016102ba83858361123d565b5050505050565b60046020525f9081526040902080546001820180549192916102e2906111bc565b80601f016020809104026020016040519081016040528092919081815260200182805461030e906111bc565b80156103595780601f1061033057610100808354040283529160200191610359565b820191905f5260205f20905b81548152906001019060200180831161033c57829003601f168201915b50505050509080600201805461036e906111bc565b80601f016020809104026020016040519081016040528092919081815260200182805461039a906111bc565b80156103e55780601f106103bc576101008083540402835291602001916103e5565b820191905f5260205f20905b8154815290600101906020018083116103c857829003601f168201915b5050505050908060030180546103fa906111bc565b80601f0160208091040260200160405190810160405280929190818152602001828054610426906111bc565b80156104715780601f1061044857610100808354040283529160200191610471565b820191905f5260205f20905b81548152906001019060200180831161045457829003601f168201915b50505050509080600401549080600501805461048c906111bc565b80601f01602080910402602001604051908101604052809291908181526020018280546104b8906111bc565b80156105035780601f106104da57610100808354040283529160200191610503565b820191905f5260205f20905b8154815290600101906020018083116104e657829003601f168201915b505050505090806006018054610518906111bc565b80601f0160208091040260200160405190810160405280929190818152602001828054610544906111bc565b801561058f5780601f106105665761010080835404028352916020019161058f565b820191905f5260205f20905b81548152906001019060200180831161057257829003601f168201915b5050505060078301546008840154600a90940154929390926001600160a01b03909116915060ff168a565b335f9081526002602052604081205460ff166106185760405162461bcd60e51b815260206004820152601a60248201527f526571756965726520726f6c205452414e53504f5254495354410000000000006044820152606401610242565b5f878152600460205260409020600a81015460ff166106495760405162461bcd60e51b81526004016102429061117a565b60088101546001600160a01b031633146106a55760405162461bcd60e51b815260206004820152601a60248201527f4e6f206573206f776e65722064656c207472616e73706f7274650000000000006044820152606401610242565b5f5b8681101561079c575f8888838181106106c2576106c26112f7565b600154604051639c4a91bd60e01b815260209290920293909301356004820181905293506001600160a01b0390921691639c4a91bd9150602401602060405180830381865afa158015610717573d5f5f3e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061073b919061130b565b6107935760405162461bcd60e51b8152602060048201526024808201527f456d7061717565206e6f2065786973746520736567756e204c6f7465436f6e746044820152631c9858dd60e21b6064820152608401610242565b506001016106a7565b50600580545f91826107ad83611326565b909155505f818152600660205260409020818155600181018b905560038101889055909150600481016107e186888361123d565b5060058101805460ff60a01b1933166001600160a81b031990911617600160a01b1790555f5b8881101561084e57816002018a8a83818110610825576108256112f7565b8354600180820186555f9586526020958690209290950293909301359201919091555001610807565b506040518a9083907f54a48e0a5bb93aeb62db8fa7d9df517fa6ad7156fbf1627c7dbe9b7479d439a9905f90a35098975050505050505050565b5f546001600160a01b031633146108ce5760405162461bcd60e51b815260206004820152600a60248201526929b7b6379020a226a4a760b11b6044820152606401610242565b6001600160a01b03919091165f908152600260205260409020805460ff1916911515919091179055565b5f818152600460205260409020600a015460609060ff1661092b5760405162461bcd60e51b81526004016102429061117a565b5f828152600460209081526040918290206009018054835181840281018401909452808452909183018282801561097f57602002820191905f5260205f20905b81548152602001906001019080831161096b575b50505050509050919050565b335f9081526002602052604081205460ff166109e95760405162461bcd60e51b815260206004820152601a60248201527f526571756965726520726f6c205452414e53504f5254495354410000000000006044820152606401610242565b8a610a285760405162461bcd60e51b815260206004820152600f60248201526e706c6163612072657175657269646160881b6044820152606401610242565b5f5b82811015610b1d575f848483818110610a4557610a456112f7565b600154604051639c4a91bd60e01b815260209290920293909301356004820181905293506001600160a01b0390921691639c4a91bd9150602401602060405180830381865afa158015610a9a573d5f5f3e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610abe919061130b565b610b145760405162461bcd60e51b815260206004820152602160248201527f456d7061717565206e6f2065786973746520656e204c6f7465436f6e747261636044820152601d60fa1b6064820152608401610242565b50600101610a2a565b50600380545f9182610b2e83611326565b9190505590505f60045f8381526020019081526020015f20905081815f01819055508d8d826001019182610b6392919061123d565b5060028101610b738c8e8361123d565b5060038101610b838a8c8361123d565b506004810188905560058101610b9a87898361123d565b5060408051602081019091525f81526006820190610bb8908261134a565b504260078201556008810180546001600160a01b03191633179055600a8101805460ff191660011790555f5b84811015610c2b5781600901868683818110610c0257610c026112f7565b8354600180820186555f9586526020958690209290950293909301359201919091555001610be4565b50604051339083907f2e130ed832b460f78b350c6778528e9d5d5528a9d79bf3fd366de6260c3de73b905f90a3509c9b505050505050505050505050565b60066020525f9081526040902080546001820154600383015460048401805493949293919291610c98906111bc565b80601f0160208091040260200160405190810160405280929190818152602001828054610cc4906111bc565b8015610d0f5780601f10610ce657610100808354040283529160200191610d0f565b820191905f5260205f20905b815481529060010190602001808311610cf257829003601f168201915b505050600590930154919250506001600160a01b0381169060ff600160a01b9091041686565b5f5f83601f840112610d45575f5ffd5b50813567ffffffffffffffff811115610d5c575f5ffd5b602083019150836020828501011115610d73575f5ffd5b9250929050565b5f5f5f60408486031215610d8c575f5ffd5b83359250602084013567ffffffffffffffff811115610da9575f5ffd5b610db586828701610d35565b9497909650939450505050565b5f60208284031215610dd2575f5ffd5b5035919050565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b8a815261014060208201525f610e2161014083018c610dd9565b8281036040840152610e33818c610dd9565b90508281036060840152610e47818b610dd9565b905088608084015282810360a0840152610e618189610dd9565b905082810360c0840152610e758188610dd9565b60e084019690965250506001600160a01b0392909216610100830152151561012090910152979650505050505050565b5f5f83601f840112610eb5575f5ffd5b50813567ffffffffffffffff811115610ecc575f5ffd5b6020830191508360208260051b8501011115610d73575f5ffd5b5f5f5f5f5f5f60808789031215610efb575f5ffd5b86359550602087013567ffffffffffffffff811115610f18575f5ffd5b610f2489828a01610ea5565b90965094505060408701359250606087013567ffffffffffffffff811115610f4a575f5ffd5b610f5689828a01610d35565b979a9699509497509295939492505050565b80356001600160a01b0381168114610f7e575f5ffd5b919050565b5f60208284031215610f93575f5ffd5b610f9c82610f68565b9392505050565b8015158114610fb0575f5ffd5b50565b5f5f60408385031215610fc4575f5ffd5b610fcd83610f68565b91506020830135610fdd81610fa3565b809150509250929050565b602080825282518282018190525f918401906040840190835b8181101561101f578351835260209384019390920191600101611001565b509095945050505050565b5f5f5f5f5f5f5f5f5f5f5f60c08c8e031215611044575f5ffd5b8b3567ffffffffffffffff81111561105a575f5ffd5b6110668e828f01610d35565b909c509a505060208c013567ffffffffffffffff811115611085575f5ffd5b6110918e828f01610d35565b909a5098505060408c013567ffffffffffffffff8111156110b0575f5ffd5b6110bc8e828f01610d35565b90985096505060608c0135945060808c013567ffffffffffffffff8111156110e2575f5ffd5b6110ee8e828f01610d35565b90955093505060a08c013567ffffffffffffffff81111561110d575f5ffd5b6111198e828f01610ea5565b915080935050809150509295989b509295989b9093969950565b86815285602082015284604082015260c060608201525f61115760c0830186610dd9565b6001600160a01b039490941660808301525090151560a090910152949350505050565b6020808252601490820152735472616e73706f727465206e6f2065786973746560601b604082015260600190565b634e487b7160e01b5f52604160045260245ffd5b600181811c908216806111d057607f821691505b6020821081036111ee57634e487b7160e01b5f52602260045260245ffd5b50919050565b601f82111561123857805f5260205f20601f840160051c810160208510156112195750805b601f840160051c820191505b818110156102ba575f8155600101611225565b505050565b67ffffffffffffffff831115611255576112556111a8565b6112698361126383546111bc565b836111f4565b5f601f84116001811461129a575f85156112835750838201355b5f19600387901b1c1916600186901b1783556102ba565b5f83815260208120601f198716915b828110156112c957868501358255602094850194600190920191016112a9565b50868210156112e5575f1960f88860031b161c19848701351681555b505060018560011b0183555050505050565b634e487b7160e01b5f52603260045260245ffd5b5f6020828403121561131b575f5ffd5b8151610f9c81610fa3565b5f6001820161134357634e487b7160e01b5f52601160045260245ffd5b5060010190565b815167ffffffffffffffff811115611364576113646111a8565b6113788161137284546111bc565b846111f4565b6020601f8211600181146113aa575f83156113935750848201515b5f19600385901b1c1916600184901b1784556102ba565b5f84815260208120601f198516915b828110156113d957878501518255602094850194600190920191016113b9565b50848210156113f657868401515f19600387901b60f8161c191681555b50505050600190811b0190555056fea26469706673582212202dbc635868a959e2d33ea749d323864e0eb63b7e0bb7c14242ce6d7523f73ab164736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_CARGAS = "cargas";

    public static final String FUNC_CREARCARGA = "crearCarga";

    public static final String FUNC_EXISTSTRANSPORTE = "existsTransporte";

    public static final String FUNC_GETTRANSPORTEEMPAQUES = "getTransporteEmpaques";

    public static final String FUNC_ISTRANSPORTISTA = "isTransportista";

    public static final String FUNC_LOTECONTRACT = "loteContract";

    public static final String FUNC_REGISTRARTRANSPORTE = "registrarTransporte";

    public static final String FUNC_SETTRANSPORTEHASH = "setTransporteHash";

    public static final String FUNC_SETTRANSPORTISTA = "setTransportista";

    public static final String FUNC_TRANSPORTES = "transportes";

    public static final Event CARGACREADA_EVENT = new Event("CargaCreada", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event TRANSPORTEREGISTRADO_EVENT = new Event("TransporteRegistrado", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected TransporteContract(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TransporteContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TransporteContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TransporteContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<CargaCreadaEventResponse> getCargaCreadaEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CARGACREADA_EVENT, transactionReceipt);
        ArrayList<CargaCreadaEventResponse> responses = new ArrayList<CargaCreadaEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CargaCreadaEventResponse typedResponse = new CargaCreadaEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.idCarga = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.idTransporte = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CargaCreadaEventResponse getCargaCreadaEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CARGACREADA_EVENT, log);
        CargaCreadaEventResponse typedResponse = new CargaCreadaEventResponse();
        typedResponse.log = log;
        typedResponse.idCarga = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.idTransporte = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<CargaCreadaEventResponse> cargaCreadaEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCargaCreadaEventFromLog(log));
    }

    public Flowable<CargaCreadaEventResponse> cargaCreadaEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CARGACREADA_EVENT));
        return cargaCreadaEventFlowable(filter);
    }

    public static List<TransporteRegistradoEventResponse> getTransporteRegistradoEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSPORTEREGISTRADO_EVENT, transactionReceipt);
        ArrayList<TransporteRegistradoEventResponse> responses = new ArrayList<TransporteRegistradoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransporteRegistradoEventResponse typedResponse = new TransporteRegistradoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.idTransporte = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.transportista = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransporteRegistradoEventResponse getTransporteRegistradoEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSPORTEREGISTRADO_EVENT, log);
        TransporteRegistradoEventResponse typedResponse = new TransporteRegistradoEventResponse();
        typedResponse.log = log;
        typedResponse.idTransporte = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.transportista = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<TransporteRegistradoEventResponse> transporteRegistradoEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransporteRegistradoEventFromLog(log));
    }

    public Flowable<TransporteRegistradoEventResponse> transporteRegistradoEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSPORTEREGISTRADO_EVENT));
        return transporteRegistradoEventFlowable(filter);
    }

    public RemoteFunctionCall<String> admin() {
        final Function function = new Function(FUNC_ADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, BigInteger, BigInteger, String, String, Boolean>> cargas(
            BigInteger param0) {
        final Function function = new Function(FUNC_CARGAS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, BigInteger, BigInteger, String, String, Boolean>>(function,
                new Callable<Tuple6<BigInteger, BigInteger, BigInteger, String, String, Boolean>>() {
                    @Override
                    public Tuple6<BigInteger, BigInteger, BigInteger, String, String, Boolean> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, BigInteger, BigInteger, String, String, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> crearCarga(BigInteger transporteId,
            List<BigInteger> empaquesIds, BigInteger fechaCarga, String lugarCarga) {
        final Function function = new Function(
                FUNC_CREARCARGA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(transporteId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(empaquesIds, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(fechaCarga), 
                new org.web3j.abi.datatypes.Utf8String(lugarCarga)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> existsTransporte(BigInteger transporteId) {
        final Function function = new Function(FUNC_EXISTSTRANSPORTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(transporteId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<List> getTransporteEmpaques(BigInteger transporteId) {
        final Function function = new Function(FUNC_GETTRANSPORTEEMPAQUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(transporteId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Boolean> isTransportista(String param0) {
        final Function function = new Function(FUNC_ISTRANSPORTISTA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> loteContract() {
        final Function function = new Function(FUNC_LOTECONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registrarTransporte(String placaVehiculo,
            String lugarSalida, String destino, BigInteger fechaSalida, String descripcion,
            List<BigInteger> empaquesIds) {
        final Function function = new Function(
                FUNC_REGISTRARTRANSPORTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(placaVehiculo), 
                new org.web3j.abi.datatypes.Utf8String(lugarSalida), 
                new org.web3j.abi.datatypes.Utf8String(destino), 
                new org.web3j.abi.datatypes.generated.Uint256(fechaSalida), 
                new org.web3j.abi.datatypes.Utf8String(descripcion), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(empaquesIds, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTransporteHash(BigInteger transporteId,
            String hash) {
        final Function function = new Function(
                FUNC_SETTRANSPORTEHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(transporteId), 
                new org.web3j.abi.datatypes.Utf8String(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTransportista(String account,
            Boolean allowed) {
        final Function function = new Function(
                FUNC_SETTRANSPORTISTA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account), 
                new org.web3j.abi.datatypes.Bool(allowed)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple10<BigInteger, String, String, String, BigInteger, String, String, BigInteger, String, Boolean>> transportes(
            BigInteger param0) {
        final Function function = new Function(FUNC_TRANSPORTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple10<BigInteger, String, String, String, BigInteger, String, String, BigInteger, String, Boolean>>(function,
                new Callable<Tuple10<BigInteger, String, String, String, BigInteger, String, String, BigInteger, String, Boolean>>() {
                    @Override
                    public Tuple10<BigInteger, String, String, String, BigInteger, String, String, BigInteger, String, Boolean> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<BigInteger, String, String, String, BigInteger, String, String, BigInteger, String, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (String) results.get(8).getValue(), 
                                (Boolean) results.get(9).getValue());
                    }
                });
    }

    @Deprecated
    public static TransporteContract load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TransporteContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TransporteContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TransporteContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TransporteContract load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TransporteContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TransporteContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TransporteContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TransporteContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _loteContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _loteContractAddress)));
        return deployRemoteCall(TransporteContract.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<TransporteContract> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String _loteContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _loteContractAddress)));
        return deployRemoteCall(TransporteContract.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TransporteContract> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _loteContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _loteContractAddress)));
        return deployRemoteCall(TransporteContract.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TransporteContract> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String _loteContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _loteContractAddress)));
        return deployRemoteCall(TransporteContract.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class CargaCreadaEventResponse extends BaseEventResponse {
        public BigInteger idCarga;

        public BigInteger idTransporte;
    }

    public static class TransporteRegistradoEventResponse extends BaseEventResponse {
        public BigInteger idTransporte;

        public String transportista;
    }
}
