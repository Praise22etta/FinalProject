package com.example.myfianlyearproject;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
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
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
@SuppressWarnings("rawtypes")
public class DonationTracker extends Contract {
    public static final String BINARY = "..."; // Keep the binary unchanged if it's already present
    public static final String FUNC_DONATIONS = "donations";
    public static final String FUNC_GETDONATION = "getDonation";
    public static final String FUNC_GETDONATIONCOUNT = "getDonationCount";
    public static final String FUNC_LOGDONATION = "logDonation";
    public static final Event DONATIONLOGGED_EVENT = new Event("DonationLogged",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Utf8String>() {},
                    new TypeReference<Utf8String>() {},
                    new TypeReference<Utf8String>() {},
                    new TypeReference<Utf8String>() {}));
    protected DonationTracker(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }
    protected DonationTracker(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }
    protected DonationTracker(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
    protected DonationTracker(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }
    public static List<DonationLoggedEventResponse> getDonationLoggedEvents(TransactionReceipt receipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DONATIONLOGGED_EVENT, receipt);
        List<DonationLoggedEventResponse> responses = new ArrayList<>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DonationLoggedEventResponse response = new DonationLoggedEventResponse();
            response.log = eventValues.getLog();
            response.donor = (String) eventValues.getNonIndexedValues().get(0).getValue();
            response.donationType = (String) eventValues.getNonIndexedValues().get(1).getValue();
            response.timestamp = (String) eventValues.getNonIndexedValues().get(2).getValue();
            response.itemsJson = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(response);
        }
        return responses;
    }
    public static DonationLoggedEventResponse getDonationLoggedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DONATIONLOGGED_EVENT, log);
        DonationLoggedEventResponse response = new DonationLoggedEventResponse();
        response.log = log;
        response.donor = (String) eventValues.getNonIndexedValues().get(0).getValue();
        response.donationType = (String) eventValues.getNonIndexedValues().get(1).getValue();
        response.timestamp = (String) eventValues.getNonIndexedValues().get(2).getValue();
        response.itemsJson = (String) eventValues.getNonIndexedValues().get(3).getValue();
        return response;
    }
    public Flowable<DonationLoggedEventResponse> donationLoggedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(DonationTracker::getDonationLoggedEventFromLog);
    }
    public Flowable<DonationLoggedEventResponse> donationLoggedEventFlowable(DefaultBlockParameter start, DefaultBlockParameter end) {
        EthFilter filter = new EthFilter(start, end, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DONATIONLOGGED_EVENT));
        return donationLoggedEventFlowable(filter);
    }
    public RemoteFunctionCall<Tuple4<String, String, String, String>> donations(BigInteger index) {
        final Function function = new Function(FUNC_DONATIONS,
                Arrays.<Type>asList(new Uint256(index)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<>(function, () -> {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple4<>(
                    (String) results.get(0).getValue(),
                    (String) results.get(1).getValue(),
                    (String) results.get(2).getValue(),
                    (String) results.get(3).getValue());
        });
    }
    public RemoteFunctionCall<BigInteger> getDonationCount() {
        final Function function = new Function(FUNC_GETDONATIONCOUNT, Collections.emptyList(), Arrays.asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }
    public RemoteFunctionCall<TransactionReceipt> logDonation(String donor, String type, String timestamp, String itemsJson) {
        final Function function = new Function(FUNC_LOGDONATION,
                Arrays.asList(new Utf8String(donor), new Utf8String(type), new Utf8String(timestamp), new Utf8String(itemsJson)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }
    public static RemoteCall<DonationTracker> deploy(Web3j web3j, Credentials credentials, ContractGasProvider provider) {
        return deployRemoteCall(DonationTracker.class, web3j, credentials, provider, BINARY, "");
    }
    public static RemoteCall<DonationTracker> deploy(Web3j web3j, TransactionManager manager, ContractGasProvider provider) {
        return deployRemoteCall(DonationTracker.class, web3j, manager, provider, BINARY, "");
    }
    @Deprecated
    public static DonationTracker load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DonationTracker(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }
    @Deprecated
    public static DonationTracker load(String contractAddress, Web3j web3j, TransactionManager manager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DonationTracker(contractAddress, web3j, manager, gasPrice, gasLimit);
    }
    public static DonationTracker load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider provider) {
        return new DonationTracker(contractAddress, web3j, credentials, provider);
    }
    public static DonationTracker load(String contractAddress, Web3j web3j, TransactionManager manager, ContractGasProvider provider) {
        return new DonationTracker(contractAddress, web3j, manager, provider);
    }
    public static class DonationLoggedEventResponse extends BaseEventResponse {
        public String donor;
        public String donationType;
        public String timestamp;
        public String itemsJson;
    }
}