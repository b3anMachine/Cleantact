package com.example.ednunez.myapplication;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by ednunez on 6/14/2015.
 */
public class Presenter {

    private final Model model;
    private final ArrayList<Contact> contacts;

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public Presenter(MainActivity view){
        model = new Model(view);
        contacts = model.getContacts();
    }

    public void deleteContact(Uri uri){
        model.deleteContact(uri);
    }

}
