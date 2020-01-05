package com.example.firestorerecycleradapterdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText username,password;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getemail = username.getText().toString().trim();
                String getepassword = password.getText().toString().trim();

                if(getemail.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Type Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(getemail).matches()){
                    Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(getepassword.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Type Password", Toast.LENGTH_SHORT).show();
                    return;
                }


                callsignin(getemail,getepassword);

            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }


    private void callsignin(String email,String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "sign In Successful:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w("TESTING", "signInWithEmail:failed", task.getException());

                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent i = new Intent(LoginActivity.this, ChatActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
    }

}
