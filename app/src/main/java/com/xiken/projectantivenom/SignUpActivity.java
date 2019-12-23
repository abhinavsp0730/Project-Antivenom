package com.xiken.projectantivenom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth ;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button signUP;
//    DatabaseReference databaseReference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.verification_code);
        password = findViewById(R.id.sign_in_password);
        firebaseAuth = FirebaseAuth.getInstance();
        confirmPassword = findViewById(R.id.confirm_password);
        signUP = findViewById(R.id.create_account);
        progressBar = findViewById(R.id.sign_up_progressBar);
//        databaseReference = FirebaseDatabase.getInstance().getReference();
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
        signUP.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
//
//        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    Users users = new Users(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString());
//                    String uid = FirebaseAuth.getInstance().getUid();
//                    databaseReference.child("userList/"+uid).push().setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(SignUpActivity.this,"Account was created successfully",Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.INVISIBLE);
//                            Intent intent = new Intent(SignUpActivity.this,nav_activity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//
//                        }
//                    });
//                }else {
//                    signUP.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.INVISIBLE);
//                    task.getException().getMessage();
//                    Toast.makeText(SignUpActivity.this,"Some error occurs\n Please retry again",Toast.LENGTH_LONG).show();
//                }
//            }
//        });

    }

}
