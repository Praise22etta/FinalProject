package com.example.myfianlyearproject;

        import android.content.Context;
        import org.web3j.crypto.Credentials;

public class CredentialsManager {
    public static Credentials getCredentials(Context context) {
        String privateKey = "d51b1125f4f11cf0ccc347b2f71d8d5caa830f2b7b9e7d2b165f8f60390a6c0c";
        return Credentials.create(privateKey);
    }
}
