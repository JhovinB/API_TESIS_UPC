package com.upc.proyecto.trazabilidad.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
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
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple7;
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
public class LoteContract extends Contract {
    public static final String BINARY = "6080604052600160045560016005553480156018575f5ffd5b506116d2806100265f395ff3fe608060405234801561000f575f5ffd5b50600436106100a6575f3560e01c80638f8cdecf1161006e5780638f8cdecf146101555780639c4a91bd1461017b5780639f32041b146101b7578063b62052a9146101dc578063f56bf763146101f1578063f7ca7dfd146101fa575f5ffd5b80630759dbfa146100aa5780632083883a146100e85780633da848cb146100f1578063569bd5b014610104578063883f4d2d1461012f575b5f5ffd5b6100d56100b8366004610f7e565b805160208183018101805160028252928201919093012091525481565b6040519081526020015b60405180910390f35b6100d560055481565b6100d56100ff3660046110d5565b61021d565b6100d5610112366004610f7e565b805160208183018101805160038252928201919093012091525481565b61014261013d3660046111ec565b6106fa565b6040516100df9796959493929190611231565b6101686101633660046111ec565b61084f565b6040516100df979695949392919061128c565b6101a76101893660046111ec565b5f90815260208190526040902060050154600160a01b900460ff1690565b60405190151581526020016100df565b6101ca6101c53660046111ec565b610a21565b6040516100df969594939291906112ec565b6101e4610c60565b6040516100df9190611343565b6100d560045481565b61020d6102083660046111ec565b610d34565b6040516100df94939291906113a6565b5f60028860405161022e91906113e2565b9081526020016040518091039020545f146102855760405162461bcd60e51b81526020600482015260126024820152714c6f7465207961207265676973747261646f60701b60448201526064015b60405180910390fd5b82518451148015610297575081518351145b6102e35760405162461bcd60e51b815260206004820181905260248201527f4461746f7320646520656d70617175657320696e636f6e73697374656e746573604482015260640161027c565b5f8451116103335760405162461bcd60e51b815260206004820181905260248201527f4465626520696e636c75697220616c206d656e6f7320756e20656d7061717565604482015260640161027c565b600480545f9182610343836113f8565b909155505f81815260016020819052604090912082815591925081016103698b826114a0565b50600281016103788a826114a0565b506003810161038789826114a0565b5060048101805488151560ff199182161790915542600583015560078201805490911660011790555f5b86518110156106535760038782815181106103ce576103ce61155d565b60200260200101516040516103e391906113e2565b9081526020016040518091039020545f146104395760405162461bcd60e51b8152602060048201526016602482015275636f6469676f5152207961207265676973747261646f60501b604482015260640161027c565b600580545f9182610449836113f8565b909155505f8181526020819052604090208181558951919250908990849081106104755761047561155d565b602002602001015181600101908161048d91906114a0565b508783815181106104a0576104a061155d565b602002602001015181600201819055508683815181106104c2576104c261155d565b60200260200101518160030190816104da91906114a0565b506004810185905560058101805460ff60a01b1933166001600160a81b031990911617600160a01b179055885182906003908b908690811061051e5761051e61155d565b602002602001015160405161053391906113e2565b908152604051602091819003820190209190915560068581018054600180820183555f9283529390912084549190920290910190815582918181019061057b90840182611571565b50600282015481600201556003820181600301908161059a9190611571565b5060048281015490820155600591820180549290910180546001600160a01b031981166001600160a01b039094169384178255915460ff600160a01b91829004161515026001600160a81b0319909216909217179055885182907f7daebd776718e60434159d15afd48dc38c0769dc46f58e03323cb56b51ce2cda908b90869081106106285761062861155d565b602002602001015187336040516106419392919061163d565b60405180910390a250506001016103b1565b508160028b60405161066591906113e2565b90815260405190819003602001902055600680546001810182555f919091527ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f016106b08b826114a0565b50817f740edd25e50f6a8b29abc575ef108385af69e1f8a372b71525161b69c03c387a8b33426040516106e59392919061166f565b60405180910390a25098975050505050505050565b5f602081905290815260409020805460018201805491929161071b9061141c565b80601f01602080910402602001604051908101604052809291908181526020018280546107479061141c565b80156107925780601f1061076957610100808354040283529160200191610792565b820191905f5260205f20905b81548152906001019060200180831161077557829003601f168201915b5050505050908060020154908060030180546107ad9061141c565b80601f01602080910402602001604051908101604052809291908181526020018280546107d99061141c565b80156108245780601f106107fb57610100808354040283529160200191610824565b820191905f5260205f20905b81548152906001019060200180831161080757829003601f168201915b5050505060048301546005909301549192916001600160a01b0381169150600160a01b900460ff1687565b600160208190525f91825260409091208054918101805461086f9061141c565b80601f016020809104026020016040519081016040528092919081815260200182805461089b9061141c565b80156108e65780601f106108bd576101008083540402835291602001916108e6565b820191905f5260205f20905b8154815290600101906020018083116108c957829003601f168201915b5050505050908060020180546108fb9061141c565b80601f01602080910402602001604051908101604052809291908181526020018280546109279061141c565b80156109725780601f1061094957610100808354040283529160200191610972565b820191905f5260205f20905b81548152906001019060200180831161095557829003601f168201915b5050505050908060030180546109879061141c565b80601f01602080910402602001604051908101604052809291908181526020018280546109b39061141c565b80156109fe5780601f106109d5576101008083540402835291602001916109fe565b820191905f5260205f20905b8154815290600101906020018083116109e157829003601f168201915b5050505060048301546005840154600790940154929360ff918216939092501687565b5f8181526001602052604081206007810154606092839283928291829160ff16610a825760405162461bcd60e51b81526020600482015260126024820152714c6f7465206e6f20656e636f6e747261646f60701b604482015260640161027c565b806001018160020182600301836004015f9054906101000a900460ff1684600501548560060180549050858054610ab89061141c565b80601f0160208091040260200160405190810160405280929190818152602001828054610ae49061141c565b8015610b2f5780601f10610b0657610100808354040283529160200191610b2f565b820191905f5260205f20905b815481529060010190602001808311610b1257829003601f168201915b50505050509550848054610b429061141c565b80601f0160208091040260200160405190810160405280929190818152602001828054610b6e9061141c565b8015610bb95780601f10610b9057610100808354040283529160200191610bb9565b820191905f5260205f20905b815481529060010190602001808311610b9c57829003601f168201915b50505050509450838054610bcc9061141c565b80601f0160208091040260200160405190810160405280929190818152602001828054610bf89061141c565b8015610c435780601f10610c1a57610100808354040283529160200191610c43565b820191905f5260205f20905b815481529060010190602001808311610c2657829003601f168201915b505050505093509650965096509650965096505091939550919395565b60606006805480602002602001604051908101604052809291908181526020015f905b82821015610d2b578382905f5260205f20018054610ca09061141c565b80601f0160208091040260200160405190810160405280929190818152602001828054610ccc9061141c565b8015610d175780601f10610cee57610100808354040283529160200191610d17565b820191905f5260205f20905b815481529060010190602001808311610cfa57829003601f168201915b505050505081526020019060010190610c83565b50505050905090565b5f8181526020819052604081206005810154606092918391839190600160a01b900460ff16610d995760405162461bcd60e51b8152602060048201526011602482015270456d7061717565206e6f2065786973746560781b604482015260640161027c565b806001018160020154826003018360040154838054610db79061141c565b80601f0160208091040260200160405190810160405280929190818152602001828054610de39061141c565b8015610e2e5780601f10610e0557610100808354040283529160200191610e2e565b820191905f5260205f20905b815481529060010190602001808311610e1157829003601f168201915b50505050509350818054610e419061141c565b80601f0160208091040260200160405190810160405280929190818152602001828054610e6d9061141c565b8015610eb85780601f10610e8f57610100808354040283529160200191610eb8565b820191905f5260205f20905b815481529060010190602001808311610e9b57829003601f168201915b505050505091509450945094509450509193509193565b634e487b7160e01b5f52604160045260245ffd5b604051601f8201601f191681016001600160401b0381118282101715610f0b57610f0b610ecf565b604052919050565b5f82601f830112610f22575f5ffd5b81356001600160401b03811115610f3b57610f3b610ecf565b610f4e601f8201601f1916602001610ee3565b818152846020838601011115610f62575f5ffd5b816020850160208301375f918101602001919091529392505050565b5f60208284031215610f8e575f5ffd5b81356001600160401b03811115610fa3575f5ffd5b610faf84828501610f13565b949350505050565b80358015158114610fc6575f5ffd5b919050565b5f6001600160401b03821115610fe357610fe3610ecf565b5060051b60200190565b5f82601f830112610ffc575f5ffd5b813561100f61100a82610fcb565b610ee3565b8082825260208201915060208360051b860101925085831115611030575f5ffd5b602085015b838110156110705780356001600160401b03811115611052575f5ffd5b611061886020838a0101610f13565b84525060209283019201611035565b5095945050505050565b5f82601f830112611089575f5ffd5b813561109761100a82610fcb565b8082825260208201915060208360051b8601019250858311156110b8575f5ffd5b602085015b838110156110705780358352602092830192016110bd565b5f5f5f5f5f5f5f60e0888a0312156110eb575f5ffd5b87356001600160401b03811115611100575f5ffd5b61110c8a828b01610f13565b97505060208801356001600160401b03811115611127575f5ffd5b6111338a828b01610f13565b96505060408801356001600160401b0381111561114e575f5ffd5b61115a8a828b01610f13565b95505061116960608901610fb7565b935060808801356001600160401b03811115611183575f5ffd5b61118f8a828b01610fed565b93505060a08801356001600160401b038111156111aa575f5ffd5b6111b68a828b0161107a565b92505060c08801356001600160401b038111156111d1575f5ffd5b6111dd8a828b01610fed565b91505092959891949750929550565b5f602082840312156111fc575f5ffd5b5035919050565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b87815260e060208201525f61124960e0830189611203565b87604084015282810360608401526112618188611203565b608084019690965250506001600160a01b039290921660a0830152151560c090910152949350505050565b87815260e060208201525f6112a460e0830189611203565b82810360408401526112b68189611203565b905082810360608401526112ca8188611203565b9515156080840152505060a0810192909252151560c090910152949350505050565b60c081525f6112fe60c0830189611203565b82810360208401526113108189611203565b905082810360408401526113248188611203565b95151560608401525050608081019290925260a0909101529392505050565b5f602082016020835280845180835260408501915060408160051b8601019250602086015f5b8281101561139a57603f19878603018452611385858351611203565b94506020938401939190910190600101611369565b50929695505050505050565b608081525f6113b86080830187611203565b85602084015282810360408401526113d08186611203565b91505082606083015295945050505050565b5f82518060208501845e5f920191825250919050565b5f6001820161141557634e487b7160e01b5f52601160045260245ffd5b5060010190565b600181811c9082168061143057607f821691505b60208210810361144e57634e487b7160e01b5f52602260045260245ffd5b50919050565b601f82111561149b57805f5260205f20601f840160051c810160208510156114795750805b601f840160051c820191505b81811015611498575f8155600101611485565b50505b505050565b81516001600160401b038111156114b9576114b9610ecf565b6114cd816114c7845461141c565b84611454565b6020601f821160018114611502575f83156114e85750848201515b600184901b5f19600386901b1c198216175b855550611498565b5f84815260208120601f198516915b828110156115315787850151825560209485019460019092019101611511565b508482101561154e57868401515f19600387901b60f8161c191681555b50505050600190811b01905550565b634e487b7160e01b5f52603260045260245ffd5b81810361157c575050565b611586825461141c565b6001600160401b0381111561159d5761159d610ecf565b6115ab816114c7845461141c565b5f601f8211600181146115da575f83156114e8575081850154600184901b5f19600386901b1c198216176114fa565b5f8581526020808220868352908220601f198616925b8381101561161057828601548255600195860195909101906020016115f0565b508583101561162d57818501545f19600388901b60f8161c191681555b5050505050600190811b01905550565b606081525f61164f6060830186611203565b6020830194909452506001600160a01b0391909116604090910152919050565b606081525f6116816060830186611203565b6001600160a01b03949094166020830152506040015291905056fea2646970667358221220b6b7efd1350ce3f1276c8f06e2529f4e150426cec03e385a51020ee6224b672d64736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_EMPAQUEIDBYCODIGO = "empaqueIdByCodigo";

    public static final String FUNC_EMPAQUES = "empaques";

    public static final String FUNC_EXISTSEMPAQUE = "existsEmpaque";

    public static final String FUNC_LOTEIDBYCODIGO = "loteIdByCodigo";

    public static final String FUNC_LOTES = "lotes";

    public static final String FUNC_NEXTEMPAQUEID = "nextEmpaqueId";

    public static final String FUNC_NEXTLOTEID = "nextLoteId";

    public static final String FUNC_OBTENERCODIGOSLOTES = "obtenerCodigosLotes";

    public static final String FUNC_OBTENEREMPAQUE = "obtenerEmpaque";

    public static final String FUNC_OBTENERLOTE = "obtenerLote";

    public static final String FUNC_REGISTRARLOTE = "registrarLote";

    public static final Event EMPAQUECREADO_EVENT = new Event("EmpaqueCreado", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event LOTEREGISTRADO_EVENT = new Event("LoteRegistrado", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected LoteContract(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LoteContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected LoteContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected LoteContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<EmpaqueCreadoEventResponse> getEmpaqueCreadoEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EMPAQUECREADO_EVENT, transactionReceipt);
        ArrayList<EmpaqueCreadoEventResponse> responses = new ArrayList<EmpaqueCreadoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EmpaqueCreadoEventResponse typedResponse = new EmpaqueCreadoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.empaqueId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.codigoQR = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.loteId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.registradoPor = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EmpaqueCreadoEventResponse getEmpaqueCreadoEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EMPAQUECREADO_EVENT, log);
        EmpaqueCreadoEventResponse typedResponse = new EmpaqueCreadoEventResponse();
        typedResponse.log = log;
        typedResponse.empaqueId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.codigoQR = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.loteId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.registradoPor = (String) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<EmpaqueCreadoEventResponse> empaqueCreadoEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEmpaqueCreadoEventFromLog(log));
    }

    public Flowable<EmpaqueCreadoEventResponse> empaqueCreadoEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EMPAQUECREADO_EVENT));
        return empaqueCreadoEventFlowable(filter);
    }

    public static List<LoteRegistradoEventResponse> getLoteRegistradoEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOTEREGISTRADO_EVENT, transactionReceipt);
        ArrayList<LoteRegistradoEventResponse> responses = new ArrayList<LoteRegistradoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LoteRegistradoEventResponse typedResponse = new LoteRegistradoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.loteId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.codigoLote = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.registradoPor = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.fechaRegistro = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LoteRegistradoEventResponse getLoteRegistradoEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOTEREGISTRADO_EVENT, log);
        LoteRegistradoEventResponse typedResponse = new LoteRegistradoEventResponse();
        typedResponse.log = log;
        typedResponse.loteId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.codigoLote = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.registradoPor = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.fechaRegistro = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<LoteRegistradoEventResponse> loteRegistradoEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLoteRegistradoEventFromLog(log));
    }

    public Flowable<LoteRegistradoEventResponse> loteRegistradoEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOTEREGISTRADO_EVENT));
        return loteRegistradoEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> empaqueIdByCodigo(String param0) {
        final Function function = new Function(FUNC_EMPAQUEIDBYCODIGO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<BigInteger, String, BigInteger, String, BigInteger, String, Boolean>> empaques(
            BigInteger param0) {
        final Function function = new Function(FUNC_EMPAQUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple7<BigInteger, String, BigInteger, String, BigInteger, String, Boolean>>(function,
                new Callable<Tuple7<BigInteger, String, BigInteger, String, BigInteger, String, Boolean>>() {
                    @Override
                    public Tuple7<BigInteger, String, BigInteger, String, BigInteger, String, Boolean> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, BigInteger, String, BigInteger, String, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> existsEmpaque(BigInteger empaqueId) {
        final Function function = new Function(FUNC_EXISTSEMPAQUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(empaqueId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> loteIdByCodigo(String param0) {
        final Function function = new Function(FUNC_LOTEIDBYCODIGO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<BigInteger, String, String, String, Boolean, BigInteger, Boolean>> lotes(
            BigInteger param0) {
        final Function function = new Function(FUNC_LOTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple7<BigInteger, String, String, String, Boolean, BigInteger, Boolean>>(function,
                new Callable<Tuple7<BigInteger, String, String, String, Boolean, BigInteger, Boolean>>() {
                    @Override
                    public Tuple7<BigInteger, String, String, String, Boolean, BigInteger, Boolean> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, String, String, Boolean, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> nextEmpaqueId() {
        final Function function = new Function(FUNC_NEXTEMPAQUEID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> nextLoteId() {
        final Function function = new Function(FUNC_NEXTLOTEID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> obtenerCodigosLotes() {
        final Function function = new Function(FUNC_OBTENERCODIGOSLOTES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
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

    public RemoteFunctionCall<Tuple4<String, BigInteger, String, BigInteger>> obtenerEmpaque(
            BigInteger empaqueId) {
        final Function function = new Function(FUNC_OBTENEREMPAQUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(empaqueId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<String, BigInteger, String, BigInteger>>(function,
                new Callable<Tuple4<String, BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple4<String, BigInteger, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, BigInteger, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple6<String, String, String, Boolean, BigInteger, BigInteger>> obtenerLote(
            BigInteger loteId) {
        final Function function = new Function(FUNC_OBTENERLOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(loteId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, String, Boolean, BigInteger, BigInteger>>(function,
                new Callable<Tuple6<String, String, String, Boolean, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<String, String, String, Boolean, BigInteger, BigInteger> call()
                            throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, Boolean, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> registrarLote(String _codigoLote, String _especie,
            String _fechaCosecha, Boolean _escanearQR, List<String> _codigosQR,
            List<BigInteger> _pesos, List<String> _unidades) {
        final Function function = new Function(
                FUNC_REGISTRARLOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_codigoLote), 
                new org.web3j.abi.datatypes.Utf8String(_especie), 
                new org.web3j.abi.datatypes.Utf8String(_fechaCosecha), 
                new org.web3j.abi.datatypes.Bool(_escanearQR), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.datatypes.Utf8String.class,
                        org.web3j.abi.Utils.typeMap(_codigosQR, org.web3j.abi.datatypes.Utf8String.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_pesos, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.datatypes.Utf8String.class,
                        org.web3j.abi.Utils.typeMap(_unidades, org.web3j.abi.datatypes.Utf8String.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static LoteContract load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new LoteContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static LoteContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LoteContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static LoteContract load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new LoteContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static LoteContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new LoteContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<LoteContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(LoteContract.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<LoteContract> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(LoteContract.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<LoteContract> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(LoteContract.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<LoteContract> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(LoteContract.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

    public static class EmpaqueCreadoEventResponse extends BaseEventResponse {
        public BigInteger empaqueId;

        public String codigoQR;

        public BigInteger loteId;

        public String registradoPor;
    }

    public static class LoteRegistradoEventResponse extends BaseEventResponse {
        public BigInteger loteId;

        public String codigoLote;

        public String registradoPor;

        public BigInteger fechaRegistro;
    }
}
