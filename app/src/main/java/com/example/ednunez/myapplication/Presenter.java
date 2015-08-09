package com.example.ednunez.myapplication;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by ednunez on 6/14/2015.
 */
public class Presenter {

    private final Model model;
    private final MainActivity view;
    private final ArrayList<Contact> contacts;
    private final ArrayAdapter<Contact> adapter;

    public Presenter(MainActivity view) {
        this.view = view;
        model = new Model(view);
        contacts = model.getContacts();
        adapter = new ArrayAdapter<Contact>(view, R.layout.item, R.id.helloText, contacts);
    }

    public void deleteContact(Contact contact) {
        boolean success = model.deleteContact(contact);
        if (success)
            adapter.notifyDataSetChanged();
    }

    public void removeFirstObjectInAdapter() {
        contacts.remove(0);
        adapter.notifyDataSetChanged();
    }

    public ArrayAdapter<Contact> getAdapter() {
        return adapter;
    }
}
