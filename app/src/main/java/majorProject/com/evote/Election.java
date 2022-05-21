package majorProject.com.evote;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class Election extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6040516108bf3803806108bf8339810160405280805160008054600160a060020a03191633600160a060020a03161790559190910190506001818051610059929160200190610060565b50506100fb565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100a157805160ff19168380011785556100ce565b828001600101855582156100ce579182015b828111156100ce5782518255916020019190600101906100b3565b506100da9291506100de565b5090565b6100f891905b808211156100da57600081556001016100e4565b90565b6107b58061010a6000396000f3006060604052600436106100a35763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630121b93f81146100a85780632e0583c8146100c05780633477ee2e1461014a5780636d81a01b146101df5780638766b9a8146101fe5780638da5cb5b1461024f578063a3ec138d1461027e578063d02cf3cf146102c3578063efbe1c1c146102e8578063fce112a9146102fb575b600080fd5b34156100b357600080fd5b6100be60043561030e565b005b34156100cb57600080fd5b6100d36103c4565b60405160208082528190810183818151815260200191508051906020019080838360005b8381101561010f5780820151838201526020016100f7565b50505050905090810190601f16801561013c5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561015557600080fd5b610160600435610462565b6040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156101a357808201518382015260200161018b565b50505050905090810190601f1680156101d05780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34156101ea57600080fd5b6100be600160a060020a036004351661052c565b341561020957600080fd5b6100be60046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965061056b95505050505050565b341561025a57600080fd5b6102626105e4565b604051600160a060020a03909116815260200160405180910390f35b341561028957600080fd5b61029d600160a060020a03600435166105f3565b604051921515835290151560208301526040808301919091526060909101905180910390f35b34156102ce57600080fd5b6102d6610619565b60405190815260200160405180910390f35b34156102f357600080fd5b6100be610620565b341561030657600080fd5b6102d6610649565b33600160a060020a0316600090815260026020526040902054610100900460ff161561033957600080fd5b600160a060020a03331660009081526002602052604090205460ff16151561036057600080fd5b600160a060020a03331660009081526002602052604090206001808201839055815461ff00191661010017909155600380548390811061039c57fe5b6000918252602090912060016002909202018101805490920190915560048054909101905550565b60018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561045a5780601f1061042f5761010080835404028352916020019161045a565b820191906000526020600020905b81548152906001019060200180831161043d57829003601f168201915b505050505081565b600380548290811061047057fe5b9060005260206000209060020201600091509050806000018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561051c5780601f106104f15761010080835404028352916020019161051c565b820191906000526020600020905b8154815290600101906020018083116104ff57829003601f168201915b5050505050908060010154905082565b60005433600160a060020a0390811691161461054757600080fd5b600160a060020a03166000908152600260205260409020805460ff19166001179055565b60005433600160a060020a0390811691161461058657600080fd5b6003805460018101610598838261064f565b9160005260206000209060020201600060408051908101604052848152600060208201529190508151819080516105d3929160200190610680565b506020820151816001015550505050565b600054600160a060020a031681565b6002602052600090815260409020805460019091015460ff808316926101009004169083565b6003545b90565b60005433600160a060020a0390811691161461063b57600080fd5b600054600160a060020a0316ff5b60045481565b81548183558181151161067b5760020281600202836000526020600020918201910161067b91906106fe565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106106c157805160ff19168380011785556106ee565b828001600101855582156106ee579182015b828111156106ee5782518255916020019190600101906106d3565b506106fa929150610728565b5090565b61061d91905b808211156106fa5760006107188282610742565b5060006001820155600201610704565b61061d91905b808211156106fa576000815560010161072e565b50805460018160011615610100020316600290046000825580601f106107685750610786565b601f0160209004906000526020600020908101906107869190610728565b505600a165627a7a72305820b730ac488e1de4956248b4db7efcd769da520cdeec09e09893ef2dc9cf2c30d40029";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_ELECTIONNAME = "electionname";

    public static final String FUNC_CANDIDATES = "candidates";

    public static final String FUNC_AUTHORIZE = "Authorize";

    public static final String FUNC_ADDCANDIDATE = "Addcandidate";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_VOTERS = "voters";

    public static final String FUNC_GETNUMCANDIDATE = "getNumCandidate";

    public static final String FUNC_END = "end";

    public static final String FUNC_TOTALVOTES = "totalvotes";

    protected Election(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Election(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> vote(BigInteger _voteIndex) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_voteIndex)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> electionname() {
        final Function function = new Function(FUNC_ELECTIONNAME, 
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple2<String, BigInteger>> candidates(BigInteger param0) {
        final Function function = new Function(FUNC_CANDIDATES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<String, BigInteger>>(
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> Authorize(String _person) {
        final Function function = new Function(
                FUNC_AUTHORIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_person)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> Addcandidate(String _name) {
        final Function function = new Function(
                FUNC_ADDCANDIDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<Boolean, Boolean, BigInteger>> voters(String param0) {
        final Function function = new Function(FUNC_VOTERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<Boolean, Boolean, BigInteger>>(
                new Callable<Tuple3<Boolean, Boolean, BigInteger>>() {
                    @Override
                    public Tuple3<Boolean, Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<Boolean, Boolean, BigInteger>(
                                (Boolean) results.get(0).getValue(),
                                (Boolean) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getNumCandidate() {
        final Function function = new Function(FUNC_GETNUMCANDIDATE, 
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> end() {
        final Function function = new Function(
                FUNC_END, 
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> totalvotes() {
        final Function function = new Function(FUNC_TOTALVOTES, 
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Election> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _name) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name)));
        return deployRemoteCall(Election.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Election> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _name) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name)));
        return deployRemoteCall(Election.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Election load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Election(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Election load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Election(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
