package com.xiken.projectantivenom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class PhoneNumberSignIn extends AppCompatActivity {
    EditText phoneNumber;
    EditText firstName;
    Button sendOtp;
    View foucusView;
    boolean cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_sign_in);
        phoneNumber = findViewById(R.id.verification_code);
        sendOtp = findViewById(R.id.create_account);
        firstName = findViewById(R.id.enter_name);
         cancel = true;
        final String phone = phoneNumber.getText().toString();
        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumber.getText().toString().isEmpty()){
                    phoneNumber.setError("Phone number is Empty");
                    cancel = false;
                    foucusView = phoneNumber;
                    return;
                }
                if (phone.length() >10){
                    phoneNumber.setError("Enter valid phone number");
                    cancel = false;
                    return;
                }
                if (cancel){

//                    Intent intent = new Intent(PhoneNumberSignIn.this,VerificationCodeActivity.class);
//                    intent.putExtra("Number",phoneNumber.getText().toString());
//                    intent.putExtra("firstName",firstName.getText().toString());
//                    startActivity(intent);
                }

            }
        });
    }

}
