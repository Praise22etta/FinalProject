package com.example.myfianlyearproject;

import android.util.Log;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;

import java.util.concurrent.CompletableFuture;

public class BlockchainLogger {
    private static final String TAG = "BlockchainLogger";
    private final Web3j web3j;
    private final Credentials credentials;
    private final ContractGasProvider gasProvider;
    private final String contractAddress;
    private final DonationTracker donationTracker;

    // Pass the contract address in when creating this object
    public BlockchainLogger(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider, String contractAddress) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.gasProvider = gasProvider;
        this.contractAddress = contractAddress;
        this.donationTracker = DonationTracker.load(contractAddress, web3j, credentials, gasProvider);
    }

    public CompletableFuture<TransactionReceipt> logDonationAsync(String donor, String donationType, String timestamp, String itemsJson) {
        try {
            return donationTracker.logDonation(donor, donationType, timestamp, itemsJson)
                    .sendAsync()
                    .whenComplete((receipt, error) -> {
                        if (error != null) {
                            Log.e(TAG, "Failed to log donation: " + error.getMessage());
                        } else {
                            Log.d(TAG, "Donation logged! Tx Hash: " + receipt.getTransactionHash());
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "Exception in logDonationAsync: " + e.getMessage());
            CompletableFuture<TransactionReceipt> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }
    }
}
