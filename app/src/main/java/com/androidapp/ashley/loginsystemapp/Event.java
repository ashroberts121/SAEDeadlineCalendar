package com.androidapp.ashley.loginsystemapp;

import java.util.ArrayList;

public class Event {

    public String date="";
    public String name="";
    public String subject="";
    public String description="";


    public static ArrayList<Event> date_collection_arr;
    public Event(String date, String name, String subject, String description){

        this.date=date;
        this.name=name;
        this.subject=subject;
        this.description= description;

    }
}
