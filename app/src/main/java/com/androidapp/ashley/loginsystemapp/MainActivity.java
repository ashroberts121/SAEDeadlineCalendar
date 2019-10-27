package com.androidapp.ashley.loginsystemapp;

import android.app.ProgressDialog;
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

public class MainActivity extends AppCompatActivity {

    //Declare view variables for main activity only
    private EditText Email;
    private EditText Password;
    private Button LoginButton;
    private TextView Info;
    private TextView userRegistration;
    private TextView forgotPassword;

    private int Counter = 5; //Failed attempt counter variable
    private FirebaseAuth firebaseAuthentication; //Declare firebase authentication object
    private ProgressDialog progressDialog; //Variable for login progress message


    //Main method - onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variables to widget IDs
        Email = (EditText)findViewById(R.id.editTextEmail);
        Password = (EditText)findViewById(R.id.editTextPassword);
        LoginButton = (Button)findViewById(R.id.buttonLogin);
        Info = (TextView)findViewById(R.id.textViewInfo);
        userRegistration = (TextView)findViewById(R.id.buttonRegisterLink);
        forgotPassword = (TextView)findViewById(R.id.textViewForgotPassword);

        Info.setText("No of Attempts Remaining: 5"); // Set text for 'info' text view

        firebaseAuthentication = FirebaseAuth.getInstance(); //Run firebase authentication instance

        progressDialog = new ProgressDialog(this); //Run instance of progress dialogue

        FirebaseUser user = firebaseAuthentication.getCurrentUser(); //Object for logged in user

        //Check for logged in user and redirect accordingly
        if(user != null){
            finish();
            startActivity(new Intent(MainActivity.this, CalendarActivity.class)); //Change to calendar activity if logged in
        }

        //Set onClickListener for login button
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateDetails()) {
                    validate(Email.getText().toString().trim(), Password.getText().toString().trim()); //On click, convert user data entry to string values and remove white space
                }
            }
        });

        //Set onClickListener for registration page link
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class)); // on Click, change to registration activity
            }
        });

        //Set onClickListener for forgot password button
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class)); // on Click, change to password activity
            }
        });
    }

    //Function for user login empty field validation toast
    private boolean validateDetails(){
        boolean result = false;

        String login_email = Email.getText().toString();
        String login_password = Password.getText().toString();

        if(login_email.isEmpty() || login_password.isEmpty()){
            Toast.makeText(this, "Please fill in all fields! :)", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

    //Login validation function
    private void validate(String userName, String userPassword){

        progressDialog.setMessage("We're logging you in! Thanks for your patience!"); //Set message for user while verifying details
        progressDialog.show(); //Run message

        //Create method for logging in
        firebaseAuthentication.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss(); //Cancels loading message on successful login
                    checkEmailVerification(); // Go to homepage activity on successful login
                }else{
                    Toast.makeText(MainActivity.this, "Login failed :(", Toast.LENGTH_SHORT).show(); //Display toast message on unsuccessful login
                    Info.setText("Login Attempts Remaining: " + Counter); //Display remaining login attempts
                    Counter--; //Decrement counter on unsuccessful login
                    if(Counter < 1){
                        LoginButton.setEnabled(false); //Disables user login if counter reaches zero remaining attempts
                    }
                    progressDialog.dismiss(); //Cancels loading message on unsuccessful login
                }
            }
        });
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuthentication.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if(emailFlag){
            finish();
            Toast.makeText(MainActivity.this, "Login successful :)", Toast.LENGTH_SHORT).show(); //Display toast message on successful login
            startActivity(new Intent(MainActivity.this, CalendarActivity.class));
        }else{
            Toast.makeText(MainActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuthentication.signOut();
        }
    }
}