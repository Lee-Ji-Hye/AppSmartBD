package import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

.;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class RoomContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50600180546001600160a01b031916331790556103d5806100326000396000f3fe6080604052600436106100555760003560e01c80635aee87031461005a57806373e048b7146100ac57806386cba22b146100d75780638da5cb5b14610125578063e377e96614610156578063f2aa821814610180575b600080fd5b34801561006657600080fd5b506100846004803603602081101561007d57600080fd5b50356101aa565b604080516001600160a01b039094168452602084019290925282820152519081900360600190f35b6100d5600480360360608110156100c257600080fd5b50803590602081013590604001356101fd565b005b3480156100e357600080fd5b506100ec6102c4565b6040518082610c8080838360005b838110156101125781810151838201526020016100fa565b5050505090500191505060405180910390f35b34801561013157600080fd5b5061013a61030a565b604080516001600160a01b039092168252519081900360200190f35b34801561016257600080fd5b506100846004803603602081101561017957600080fd5b5035610319565b34801561018c57600080fd5b5061013a600480360360208110156101a357600080fd5b5035610344565b60008060006101b7610361565b5050506000918252506020818152604091829020825160608101845281546001600160a01b03168082526001830154938201849052600290920154930183905292909190565b6001831015801561020f575060638311155b61021857600080fd5b336002846064811061022657fe5b0180546001600160a01b039283166001600160a01b03199182161790915560408051606081018252338152602080820187815282840187815260008a815292839052848320935184549088169616959095178355516001838101919091559351600290920191909155915490519216913480156108fc0292909190818181858888f193505050501580156102be573d6000803e3d6000fd5b50505050565b6102cc610381565b60408051610c808101918290529060029060649082845b81546001600160a01b031681526001909101906020018083116102e3575050505050905090565b6001546001600160a01b031681565b6000602081905290815260409020805460018201546002909201546001600160a01b03909116919083565b6002816064811061035157fe5b01546001600160a01b0316905081565b604080516060810182526000808252602082018190529181019190915290565b60405180610c800160405280606490602082028038833950919291505056fea265627a7a7231582034be6607f05739eb7e340ac4ac12677044c53d20260a467693a7ccedc7548b3d64736f6c634300050b0032";

    public static final String FUNC_GETBUYERINFO = "getBuyerInfo";

    public static final String FUNC_BUYREALESTATE = "buyRealEstate";

    public static final String FUNC_GETALLBUYERS = "getAllBuyers";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_BUYERINFO = "buyerInfo";

    public static final String FUNC_BUYERS = "buyers";

    @Deprecated
    protected RoomContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RoomContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RoomContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RoomContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple3<String, byte[], byte[]>> getBuyerInfo(BigInteger _id) {
        final Function function = new Function(FUNC_GETBUYERINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteCall<Tuple3<String, byte[], byte[]>>(
                new Callable<Tuple3<String, byte[], byte[]>>() {
                    @Override
                    public Tuple3<String, byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, byte[], byte[]>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> buyRealEstate(BigInteger _id, byte[] _name, byte[] _businessNumber, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYREALESTATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id), 
                new org.web3j.abi.datatypes.generated.Bytes32(_name), 
                new org.web3j.abi.datatypes.generated.Bytes32(_businessNumber)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<List> getAllBuyers() {
        final Function function = new Function(FUNC_GETALLBUYERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray<Address>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<String, byte[], byte[]>> buyerInfo(BigInteger param0) {
        final Function function = new Function(FUNC_BUYERINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteCall<Tuple3<String, byte[], byte[]>>(
                new Callable<Tuple3<String, byte[], byte[]>>() {
                    @Override
                    public Tuple3<String, byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, byte[], byte[]>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<String> buyers(BigInteger param0) {
        final Function function = new Function(FUNC_BUYERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<RoomContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RoomContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<RoomContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RoomContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RoomContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RoomContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RoomContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RoomContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RoomContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RoomContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RoomContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RoomContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RoomContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RoomContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RoomContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RoomContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
