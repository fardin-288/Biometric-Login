package com.example.biometricauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout mMainLayout;
    private Button LogoutButton;
    private Button LoginButton;
    private TextView textView;

    private Boolean loginStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainLayout = findViewById(R.id.main_layout);
        LogoutButton = findViewById(R.id.LogoutButton);
        LoginButton = findViewById(R.id.LoginButton);
        textView = findViewById(R.id.textView);

        if(loginStatus == false){
            BiometricLogin();
        }

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricLogin();
            }
        });

    }

    private void logout(){
        textView.setVisibility(View.INVISIBLE);
        LogoutButton.setVisibility(View.INVISIBLE);
        LoginButton.setVisibility(View.VISIBLE);
        loginStatus = false;
        BiometricLogin();
    }

    private void BiometricLogin(){
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "No fingerprint Hardware", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(getApplicationContext(), "No fingerprint Assigned", Toast.LENGTH_SHORT).show();
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                textView.setVisibility(View.VISIBLE);
                LogoutButton.setVisibility(View.VISIBLE);
                LoginButton.setVisibility(View.INVISIBLE);
                loginStatus = true;
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Project")
                .setDescription("Use FIngerprint to login").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
    }

}


