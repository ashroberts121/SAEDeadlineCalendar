package com.androidapp.ashley.loginsystemapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    //Declare password activity view variables
    private EditText passwordEmail;
    private Button passwordReset;


    private FirebaseAuth firebaseAuthentication; //Declare firebase authentication object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        //Assign variables to view IDs
        passwordEmail = (EditText)findViewById(R.id.editTextPasswordEmail);
        passwordReset = (Button)findViewById(R.id.buttonPasswordReset);

        firebaseAuthentication = FirebaseAuth.getInstance(); //Run firebase authentication instance

        //Set on click listener for password reset button
        passwordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = passwordEmail.getText().toString().trim(); //Get entered email, convert to string, remove whitespace

                if(userEmail.equals("null")){
                    Toast.makeText(PasswordActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuthentication.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this, MainActivity.class)); //Go to main activity
                            }else{
                                Toast.makeText(PasswordActivity.this, "Email not sent, please check the email you entered.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
