package com.example.biometricauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout mMainLayout;
    private Button LogoutButton;
    private Button LoginButton;
    private TextView textView;
    private  TextView textView2;

    private Boolean loginStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainLayout = findViewById(R.id.main_layout);
        LogoutButton = findViewById(R.id.LogoutButton);
        LoginButton = findViewById(R.id.LoginButton);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        if(loginStatus == false){
            BiometricLoginPrompt();
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
                BiometricLoginPrompt();
            }
        });

    }

    private void logout(){
        textView.setVisibility(View.INVISIBLE);
        LogoutButton.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        LoginButton.setVisibility(View.VISIBLE);
        loginStatus = false;
//        BiometricLogin();
    }

    private void BiometricLoginPrompt(){
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG )) {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        CombinedAuthenticate();
        builder.setTitle("Select Authentication Method");
        builder.setItems(new String[]{"Fingerprint", "Face"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    AuthenticateWithFingerPrint();
                }else if(i == 1){
                    AuthenticateWithFace();
                }
            }
        });
        builder.create().show();
    }

    private void CombinedAuthenticate(){
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
                textView2.setVisibility(View.VISIBLE);
                LoginButton.setVisibility(View.INVISIBLE);
                loginStatus = true;
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Authentication")
                .setDescription("Place FInger on Sensor or Place Face front of Camera").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void AuthenticateWithFingerPrint() {

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
                textView2.setVisibility(textView.VISIBLE);
                LoginButton.setVisibility(View.INVISIBLE);
                loginStatus = true;
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Fingerprint Authentication")
                .setDescription("Put Fingerprint on Sensor").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void AuthenticateWithFace() {

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
                textView2.setVisibility(textView.VISIBLE);
                LoginButton.setVisibility(View.INVISIBLE);
                loginStatus = true;
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Face Authentication")
                .setDescription("Place Face In-front of Camera").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
    }

}


