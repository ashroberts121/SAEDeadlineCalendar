package com.androidapp.ashley.loginsystemapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity {
    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;
    private Button button;

    private FirebaseAuth firebaseAuthentication; //Declare firebase authentication object
    private Button LogoutButton; //Declare variable for logout button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);


        firebaseAuthentication = FirebaseAuth.getInstance(); //Run firebase authentication instance

        LogoutButton =  (Button)findViewById(R.id.buttonLogOut); //Assign button widget id to button variable

        //Set on click listener for logout button (Button not used - Menu used instead)
        /*  LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout(); //Call signout function
            }
        });  */

        ////////////////////////////////////////////////////////////////////////////

        Event.date_collection_arr=new ArrayList<Event>();
        Event.date_collection_arr.add( new Event("2017-12-31" ,"New eve","Holiday","New years eve"));
        Event.date_collection_arr.add( new Event("2018-01-01" ,"New year","Holiday","New years day"));
        Event.date_collection_arr.add( new Event("2018-03-30" ,"Good Friday","Holiday","Bank Holiday"));
        Event.date_collection_arr.add( new Event("2018-04-02" ,"Easter Monday","Holiday","Bank Holiday"));
        Event.date_collection_arr.add( new Event("2018-05-07" ,"May Day","Holiday","Bank Holiday"));
        Event.date_collection_arr.add( new Event("2018-05-28" ,"Spring","Holiday","Bank Holiday"));
        Event.date_collection_arr.add( new Event("2018-07-20" ,"Review ","Deadline","Mobile Application Review Deadline"));
        Event.date_collection_arr.add( new Event("2018-07-24" ,"Mobile Presentation","Deadline","Mobile User Culture Presentation Deadline"));
        Event.date_collection_arr.add( new Event("2018-08-03" ,"Mobile Build","Deadline","Mobile Application Build Deadline"));
        Event.date_collection_arr.add( new Event("2018-08-03" ,"PHP Exam","Deadline","PHP Exam"));
        Event.date_collection_arr.add( new Event("2018-08-03" ,"Final Day of Term","Term","Last day of the Term"));
        Event.date_collection_arr.add( new Event("2018-08-06" ,"PHP CMS","Deadline","PHP CMS Deadline"));
        Event.date_collection_arr.add( new Event("2018-08-27" ,"August Bank Holiday","Holiday","Bank Holiday"));
        Event.date_collection_arr.add( new Event("2018-09-10" ,"First day of Term","Term","First day of a new Term"));
        Event.date_collection_arr.add( new Event("2018-12-07" ,"Last day of Term","Term","Last day of Term"));
        Event.date_collection_arr.add( new Event("2018-11-05" ,"Bonfire","Holiday","Bonfire Night"));
        Event.date_collection_arr.add( new Event("2018-12-24" ,"Xmas eve","Holiday","Christmas Eve"));
        Event.date_collection_arr.add( new Event("2018-12-25" ,"Xmas","Holiday","Christmas Day"));
        Event.date_collection_arr.add( new Event("2018-12-26" ,"Boxing Day","Holiday","Boxing Day"));
        Event.date_collection_arr.add( new Event("2018-12-31" ,"New eve","Holiday","New years eve"));
        Event.date_collection_arr.add( new Event("2019-01-01" ,"New year","Holiday","New years day"));



        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(this, cal_month,Event.date_collection_arr);



        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));



        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 4&&cal_month.get(GregorianCalendar.YEAR)==2017) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(CalendarActivity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 5&&cal_month.get(GregorianCalendar.YEAR)==2019) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(CalendarActivity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, CalendarActivity.this);
            }

        });
    } //End of onCreate

    //Create function for signout process
    private void Logout(){
        firebaseAuthentication.signOut(); //Run logout function on click
        finish();
        startActivity(new Intent(CalendarActivity.this, MainActivity.class)); //Change to MainActivity
    }

    private void AboutLink(){
        startActivity(new Intent(CalendarActivity.this, AboutActivity.class));//Change to About Activity
    }

    //Create function for linking to phone settings app
    private void SettingsLink(){
        startActivity(
                new Intent(Settings.ACTION_SETTINGS));
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

        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                Logout(); //Call signout function
            }
        }
        switch (item.getItemId()) {
            case R.id.aboutMenu: {
                AboutLink(); //Call about activity link function
            }
        }
        switch (item.getItemId()) {
            case R.id.settingsMenu: {
                SettingsLink(); //Call settings link function
            }
        }
        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////////////////////////////////////

    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }

}
