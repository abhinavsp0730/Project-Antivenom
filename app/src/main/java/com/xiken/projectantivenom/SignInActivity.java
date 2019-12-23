package com.xiken.projectantivenom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button signIn;
    TextView newAccout;
    FirebaseAuth firebaseAuth;
    View focusView;
    boolean cancel = false;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser()!= null){
            Intent intent = new Intent(SignInActivity.this,nav_activity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.verification_code);
        password = findViewById(R.id.sign_in_password);
        signIn = findViewById(R.id.create_account);
        newAccout = findViewById(R.id.new_account);
        progressBar = findViewById(R.id.sign_in_progressBar);
        final String emailText = email.getText().toString();
        final String passwordText = password.getText().toString();
        firebaseAuth = FirebaseAuth.getInstance();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel = false;
                if (isEmpty(email.getText().toString())){
                    email.setError("Email is empty");
                    focusView = email;
                    cancel = true;
                }
                if (!isEmailValid(email.getText().toString())){
                    email.setError("That doesn't look like email");
                    focusView = email;
                    cancel = true;
                }
                if (!isPasswordEmpty(password.getText().toString())){
                    password.setError("Password is invalid");
                    focusView = password;
                    cancel = true;
                }
                if (cancel){
                    focusView.requestFocus();
                    return;
                }
                signIn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(SignInActivity.this,"Email or password is incorrect",Toast.LENGTH_SHORT).show();
                            signIn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(SignInActivity.this,nav_activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                    }
                });

            }
        });
        newAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
    public boolean isEmailValid(String email){
        return email.contains("@");
    }
    public boolean isPasswordEmpty(String password){
        return password.length() >0;
    }
    public boolean isEmpty(String email){
        return email.length() <=0;
    }
}
