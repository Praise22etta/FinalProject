package com.example.myfianlyearproject;

import android.content.Context;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3Provider {
    private static Web3j instance;

    public static Web3j getInstance(Context context) {
        if (instance == null) {
            instance = Web3j.build(new HttpService("https://holesky.infura.io/v3/b02888be6140475984562da7c41b7828"));
        }
        return instance;
    }

}
