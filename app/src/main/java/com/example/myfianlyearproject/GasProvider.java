package com.example.myfianlyearproject;

import org.web3j.tx.gas.ContractGasProvider;
import java.math.BigInteger;

public class GasProvider implements ContractGasProvider {
    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return BigInteger.valueOf(20_000_000_000L);
    }

    @Override
    public BigInteger getGasPrice() {
        return getGasPrice(null);
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        return BigInteger.valueOf(6_700_000);
    }

    @Override
    public BigInteger getGasLimit() {
        return getGasLimit(null);
    }

    public static GasProvider getInstance() {
        return new GasProvider();
    }
}
