package com.androidapp.ashley.loginsystemapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuthentication; //Declare firebase authentication object
    private Button LogoutButton; //Declare variable for logout button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuthentication = FirebaseAuth.getInstance(); //Run firebase authentication instance

        LogoutButton =  (Button)findViewById(R.id.buttonLogOut); //Assign button widget id to button variable

        //Set on click listener for logout button
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout(); //Call signout function
            }
        });
    }

    //Create function for signout process
    private void Logout(){
        firebaseAuthentication.signOut(); //Run logout function on click
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class)); //Change to MainActivity
    }

    //Method for creating options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); //Menu inflater calls id of menu.xml
        return true;
    }
    //Method for handling menu onClick events
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.logoutMenu: {
                Logout(); //Call signout function
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
