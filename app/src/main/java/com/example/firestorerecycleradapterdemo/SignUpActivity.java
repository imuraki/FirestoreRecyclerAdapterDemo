package com.example.firestorerecycleradapterdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText firstName,lastName,email,password,confirmPassword;
    private Button signUp,cancel;
    FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName=findViewById(R.id.editTextFirstname);
        lastName=findViewById(R.id.editTextLastname);
        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        confirmPassword=findViewById(R.id.editTextConfirmPassword);
        cancel=findViewById(R.id.buttonCancel);
        signUp=findViewById(R.id.buttonSignUp);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signUp.setOnClickListener(this);
        cancel.setOnClickListener(this);

        setTitle("Sign Up");

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonCancel){
            finish();
        }else{
            String getFirstName = firstName.getText().toString().trim();
            String getLastName = lastName.getText().toString().trim();
            String getEmail = email.getText().toString().trim();
            String getPassword = password.getText().toString().trim();
            String getConfirmPassword = confirmPassword.getText().toString().trim();

            if(getFirstName.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Type First Name", Toast.LENGTH_SHORT).show();
                return;
            }
            if(getLastName.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Type Last Name", Toast.LENGTH_SHORT).show();
                return;
            }
            if(getEmail.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Type Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()){
                Toast.makeText(SignUpActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            if(getPassword.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Type Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if(getConfirmPassword.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Confirm Your Password ", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!getPassword.equals(getConfirmPassword)){
                Toast.makeText(SignUpActivity.this, "Passwords Do not match ", Toast.LENGTH_SHORT).show();
                return;
            }
            if(getPassword.length()<6){
                Toast.makeText(SignUpActivity.this, "Password should consist of minimum 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            callSignUp(getEmail,getPassword);
        }

    }
    private void callSignUp(String email,String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Signed up Failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Signed up Success, Please login", Toast.LENGTH_SHORT).show();
                            userProfile();
                        }
                    }
                });
    }

    private void userProfile()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(firstName.getText().toString().trim())
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TESTING", "User profile updated.");
                                finish();
                            }
                        }
                    });
        }
    }

}
