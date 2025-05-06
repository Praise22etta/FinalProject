package com.example.myfianlyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Hook UI components
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);

        // Button click: Navigate to Login
        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Text click: Navigate to Sign Up
        txtSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(InfoActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
