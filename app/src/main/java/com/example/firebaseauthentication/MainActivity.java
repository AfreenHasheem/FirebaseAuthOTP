package com.example.firebaseauthentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText editTextNumber, editTextVerificationCode;
    Button sendSMSButton, sendVerificationButton;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        editTextNumber = findViewById(R.id.edit_phone_number);
        editTextVerificationCode = findViewById(R.id.editTextVerificationCode);
        sendSMSButton = findViewById(R.id.sendSMSButton);
        sendVerificationButton = findViewById(R.id.sendVerificationButton);

        //initialise firebase auth
        auth = FirebaseAuth.getInstance();

        //initialize callback
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
            }
        };
    }

    public void send_sms(View v){

    }
}
