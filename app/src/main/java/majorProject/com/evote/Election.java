package majorProject.com.evote;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

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
    private static final String BINARY = "6060604052341561000f57600080fd5b604051610bfa380380610bfa8339810160405280805160008054600160a060020a03191633600160a060020a03161790559190910190506001818051610059929160200190610060565b50506100fb565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100a157805160ff19168380011785556100ce565b828001600101855582156100ce579182015b828111156100ce5782518255916020019190600101906100b3565b506100da9291506100de565b5090565b6100f891905b808211156100da57600081556001016100e4565b90565b610af08061010a6000396000f3006060604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166315597e9281146100b35780632e0583c8146101485780633477ee2e146101d25780636386a433146102d057806384c0a7b2146102f25780638da5cb5b14610305578063a3ec138d14610334578063b384abef14610381578063d02cf3cf1461039a578063efbe1c1c146103bf578063fce112a9146103d2575b600080fd5b34156100be57600080fd5b61014660046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405281815292919060208401838380828437509496506103e595505050505050565b005b341561015357600080fd5b61015b610483565b60405160208082528190810183818151815260200191508051906020019080838360005b8381101561019757808201518382015260200161017f565b50505050905090810190601f1680156101c45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156101dd57600080fd5b6101e8600435610521565b604051808060200180602001848152602001838103835286818151815260200191508051906020019080838360005b8381101561022f578082015183820152602001610217565b50505050905090810190601f16801561025c5780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b8381101561029257808201518382015260200161027a565b50505050905090810190601f1680156102bf5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b34156102db57600080fd5b610146600160a060020a0360043516602435610689565b34156102fd57600080fd5b61015b6106d9565b341561031057600080fd5b6103186107d9565b604051600160a060020a03909116815260200160405180910390f35b341561033f57600080fd5b610353600160a060020a03600435166107e8565b6040519315158452911515602084015260408084019190915260608301919091526080909101905180910390f35b341561038c57600080fd5b610146600435602435610816565b34156103a557600080fd5b6103ad610935565b60405190815260200160405180910390f35b34156103ca57600080fd5b61014661093b565b34156103dd57600080fd5b6103ad610964565b60005433600160a060020a0390811691161461040057600080fd5b6003805460018101610412838261096a565b91600052602060002090600302016000606060405190810160409081528682526020820186905260009082015291905081518190805161045692916020019061099b565b5060208201518160010190805161047192916020019061099b565b50604082015181600201555050505050565b60018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105195780601f106104ee57610100808354040283529160200191610519565b820191906000526020600020905b8154815290600101906020018083116104fc57829003601f168201915b505050505081565b600380548290811061052f57fe5b9060005260206000209060030201600091509050806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105db5780601f106105b0576101008083540402835291602001916105db565b820191906000526020600020905b8154815290600101906020018083116105be57829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106795780601f1061064e57610100808354040283529160200191610679565b820191906000526020600020905b81548152906001019060200180831161065c57829003601f168201915b5050505050908060020154905083565b60005433600160a060020a039081169116146106a457600080fd5b600160a060020a0390911660009081526002602081905260408220805460ff1916600190811782558101939093559190910155565b6106e1610a19565b33600160a060020a0316600090815260026020526040902054610100900460ff16156107425760408051908101604052600d81527f616c726561647920766f74656400000000000000000000000000000000000000602082015290506107d6565b600160a060020a03331660009081526002602052604090205460ff16151561079f5760408051908101604052600e81527f4e6f7420617574686f72697a6564000000000000000000000000000000000000602082015290506107d6565b60408051908101604052600c81527f596f75206d617920766f74650000000000000000000000000000000000000000602082015290505b90565b600054600160a060020a031681565b600260208190526000918252604090912080546001820154919092015460ff80841693610100900416919084565b33600160a060020a0316600090815260026020526040902054610100900460ff161561084157600080fd5b600160a060020a03331660009081526002602052604090205460ff16151561086857600080fd5b600160a060020a03331660009081526002602052604090206001015481141561090d57600160a060020a0333166000908152600260208190526040909120015460069010156109085760016003838154811015156108c257fe5b60009182526020808320600260039093020182018054909401909355600160a060020a033316825290915260409020805461ff0019166101001790556004805460010190555b610931565b600160a060020a033316600090815260026020819052604090912001805460010190555b5050565b60035490565b60005433600160a060020a0390811691161461095657600080fd5b600054600160a060020a0316ff5b60045481565b815481835581811511610996576003028160030283600052602060002091820191016109969190610a2b565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109dc57805160ff1916838001178555610a09565b82800160010185558215610a09579182015b82811115610a095782518255916020019190600101906109ee565b50610a15929150610a63565b5090565b60206040519081016040526000815290565b6107d691905b80821115610a15576000610a458282610a7d565b610a53600183016000610a7d565b5060006002820155600301610a31565b6107d691905b80821115610a155760008155600101610a69565b50805460018160011615610100020316600290046000825580601f10610aa35750610ac1565b601f016020900490600052602060002090810190610ac19190610a63565b505600a165627a7a72305820a819cb3cbc8f144f9c73c8dcb611dc2d5a522c6291a6ed53d4dc7bf52acf84840029";

    public static final String FUNC_ADDCANDIDATE = "Addcandidate";

    public static final String FUNC_ELECTIONNAME = "electionname";

    public static final String FUNC_CANDIDATES = "candidates";

    public static final String FUNC_AUTHORIZE = "Authorize";

    public static final String FUNC_SHOWTHEERROR = "showTheError";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_VOTERS = "voters";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_GETNUMCANDIDATE = "getNumCandidate";

    public static final String FUNC_END = "end";

    public static final String FUNC_TOTALVOTES = "totalvotes";

    protected Election(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Election(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> Addcandidate(String _name, String _link) {
        final Function function = new Function(
                FUNC_ADDCANDIDATE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name),
                        new org.web3j.abi.datatypes.Utf8String(_link)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> electionname() {
        final Function function = new Function(FUNC_ELECTIONNAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> candidates(BigInteger param0) {
        final Function function = new Function(FUNC_CANDIDATES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<String, String, BigInteger>>(
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> Authorize(String _person, BigInteger pin) {
        final Function function = new Function(
                FUNC_AUTHORIZE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_person),
                        new org.web3j.abi.datatypes.generated.Uint256(pin)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> showTheError() {
        final Function function = new Function(FUNC_SHOWTHEERROR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }



    public RemoteCall<Tuple5<Boolean, Boolean, BigInteger, BigInteger, BigInteger>> voters(String param0) {
        final Function function = new Function(FUNC_VOTERS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple5<Boolean, Boolean, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple5<Boolean, Boolean, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<Boolean, Boolean, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<Boolean, Boolean, BigInteger, BigInteger, BigInteger>(
                                (Boolean) results.get(0).getValue(),
                                (Boolean) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }


    public RemoteCall<TransactionReceipt> vote(BigInteger _voteIndex, int pin) {
        final Function function = new Function(
                FUNC_VOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_voteIndex),
                        new org.web3j.abi.datatypes.generated.Uint256(pin)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
