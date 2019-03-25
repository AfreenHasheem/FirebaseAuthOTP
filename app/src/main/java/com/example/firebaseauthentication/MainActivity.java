package com.example.firebaseauthentication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText editTextNumber, editTextVerificationCode;
    Button sendSMSButton, sendVerificationButton;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        editTextNumber = findViewById(R.id.editTextNumber);
        editTextVerificationCode = findViewById(R.id.editTextVerificationCode);
        sendSMSButton = findViewById(R.id.sendSMSButton);
        sendVerificationButton = findViewById(R.id.sendVerificationButton);

        //initialise firebase auth
        auth = FirebaseAuth.getInstance();

        //initialize callback
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                //when verification code will be sent, this method will be called
                verificationCode = s;
                Toast.makeText(MainActivity.this, "Code is sent to the number", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void send_sms(View v){
        //called when send SMS button will be clicked
        String number = editTextNumber.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number, 60, TimeUnit.SECONDS,this,mCallback
        );
    }

    public void signInWithPhone(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //user is successfully signed in
                            Toast.makeText(MainActivity.this, "User signed in successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void verify(View view){
        String inputCode = editTextVerificationCode.getText().toString();
        if(verificationCode.equals("")){
            verifyPhoneNumber(verificationCode, inputCode);
        }
    }

    private void verifyPhoneNumber(String verificationCode, String inputCode) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, inputCode);
        signInWithPhone(credential);
    }
}
