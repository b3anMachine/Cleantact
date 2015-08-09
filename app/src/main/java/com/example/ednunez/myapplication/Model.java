package com.example.ednunez.myapplication;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by ednunez on 6/17/2015.
 */
public class Model {

    private final MainActivity view;

    public Model(MainActivity view) {
        this.view = view;
    }

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.PhoneLookup.LAST_TIME_CONTACTED,
                ContactsContract.PhoneLookup.LOOKUP_KEY};
        String selection = ContactsContract.PhoneLookup.IN_VISIBLE_GROUP + " = ?";
        String selectionArgs[] = {"1"};
        String sortOrder = ContactsContract.PhoneLookup.DISPLAY_NAME;

        Cursor people = view.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projection, selection, selectionArgs, sortOrder);

        while (people.moveToNext()) {
            String name = people.getString(
                    people.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));

            long lastContacted = people.getLong(
                    people.getColumnIndex(ContactsContract.PhoneLookup.LAST_TIME_CONTACTED));

            String lookupKey = people.getString(
                    people.getColumnIndex(ContactsContract.PhoneLookup.LOOKUP_KEY));

           // InputStream photo = ContactsContract.Contacts.openContactPhotoInputStream(view.getContentResolver(), ContactsContract.Contacts.CONTENT_URI, true);

            contacts.add(
                    new Contact(lookupKey, name, lastContacted));
        }
        return contacts;
    }

    public boolean deleteContact(Contact contact) {
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, contact.getLookupKey());
        int rowsDeleted = view.getContentResolver().delete(uri, null, null);
        return rowsDeleted > 0;
    }
}
