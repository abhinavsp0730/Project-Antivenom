package com.xiken.projectantivenom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth ;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button signUP;
    DatabaseReference databaseReference;
    public static final String TAG ="volley";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_password);
        firebaseAuth = FirebaseAuth.getInstance();
        confirmPassword = findViewById(R.id.confirm_password);
        signUP = findViewById(R.id.sign_in);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError(null);
                password.setError(null);
                confirmPassword.setError(null);
                boolean cancel = false;
                View focusView = null;
                if (TextUtils.isEmpty(password.getText().toString())|| !isPassWordValid(password.getText().toString())){
                    password.setError("Password doesn't match");
                    focusView =  password;
                    cancel  = true;
                }
                if (TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Email shouldn't be null");
                    focusView = email;
                    cancel = true;
                }else if(!isEmailValid(email.getText().toString())) {
                    email.setError("It doesn't look like email");
                    focusView = email;
                    cancel = true;
                }
                if (cancel){
                    focusView.requestFocus();

                }else {
                    createFirebaseUserAccount();
                }



            }
        });



    }

    public boolean isEmailValid(String email){
        return email.contains("@");
    }
    public boolean isPassWordValid(String password){
        String confirmPassoword = confirmPassword.getText().toString();
        return password.length() > 5 && confirmPassoword.equals(password);
    }
    public void createFirebaseUserAccount(){
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "onComplete: complete");
                if (task.isSuccessful()){
                    Users users = new Users(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString());
                    String uid = FirebaseAuth.getInstance().getUid();
                    databaseReference.child("userList/"+uid).push().setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "onComplete: Regestration finish");

                        }
                    });
                }else {
                    task.getException().getMessage();
                    Log.d(TAG, "onComplete: unSuccesful");
                }
            }
        });

    }

}
