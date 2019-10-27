package com.androidapp.ashley.loginsystemapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    //Declare Registration Widget Variables for reg activity only
    private EditText Username, Password, Email, PasswordConfirm;
    private Button RegisterButton;
    private TextView LoginLink;
    private FirebaseAuth firebaseAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupUIViews(); //Run ID assign function

        firebaseAuthentication = FirebaseAuth.getInstance(); //Run firebase authentication instance

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateDetails()){

                    String user_email = Email.getText().toString().trim(); // Convert email entry to string and remove white space
                    String user_password = Password.getText().toString().trim(); // Convert password entry to string and remove white space

                    //Create onCompleteListener for successful user creation
                    firebaseAuthentication.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerification(); //Runs email verification function
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed. :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        //Link back to login activity
        LoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }
    // Function for assigning IDs
    private void setupUIViews(){

        //Assign variables to widget IDs
        Username = (EditText)findViewById(R.id.editTextUsernameRegister);
        Email = (EditText)findViewById(R.id.editTextEmailRegister);
        Password = (EditText)findViewById(R.id.editTextPasswordRegister);
        PasswordConfirm = (EditText)findViewById(R.id.editTextPasswordRegisterConfirm);
        RegisterButton = (Button)findViewById(R.id.buttonRegister);
        LoginLink = (TextView)findViewById(R.id.buttonLoginLink);
    }

    //Function for user registration empty field validation toast
    private boolean validateDetails(){
        boolean result = false; //Start with result as false

        String reg_username = Username.getText().toString();
        String reg_email = Email.getText().toString();
        String reg_password = Password.getText().toString();
        String reg_password_confirm = PasswordConfirm.getText().toString();

        if(reg_username.isEmpty() || reg_email.isEmpty() || reg_password.isEmpty() || reg_password_confirm.isEmpty()){
            Toast.makeText(this, "Please fill in all fields! :)", Toast.LENGTH_SHORT).show(); //Returns result = false if any fields are empty
        }else if(!reg_password.equals(reg_password_confirm)){
            Toast.makeText(this, "Please make sure passwords match! :)", Toast.LENGTH_SHORT).show(); //Returns result = false if password entries are different
        }else{
            result = true; //Returns result = true
        }

        return result;
    }

    //Email verification function
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuthentication.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegistrationActivity.this, "Successfully registered, validation email sent!", Toast.LENGTH_LONG).show();
                        firebaseAuthentication.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Verification email could not be sent, please check your connection.",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
