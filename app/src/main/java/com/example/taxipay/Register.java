package com.example.taxipay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Register extends AppCompatActivity {
    EditText mName, mpassword, memail, mphone;
    Button mregister;
    TextView mtext, mtext_account, mlogin;
    FirebaseAuth fAuth;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mtext = findViewById(R.id.text_taxipay);
        mtext_account = findViewById(R.id.text_newaccount);
        mName = findViewById(R.id.fullName);
        memail = findViewById(R.id.Email);
        mpassword = findViewById(R.id.password);
        mphone = findViewById(R.id.phone_number);
        mregister = findViewById(R.id.register_button);
        mlogin = findViewById(R.id.login_again);
        mProgressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    memail.setError("Invalid E-mail Address");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is required");
                    return;
                }
                if(password.length() < 6){
                    mpassword.setError(" Password must be >= 6 Characters");
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}