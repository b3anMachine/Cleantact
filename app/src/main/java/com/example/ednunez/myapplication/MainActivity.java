package com.example.ednunez.myapplication;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity {

    private ArrayList<Contact> contacts;
    private ArrayAdapter<Contact> arrayAdapter;

    @InjectView(R.id.frame)
    SwipeFlingAdapterView flingContainer;


    public ArrayList<Contact> getContacts(){
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.PhoneLookup.LAST_TIME_CONTACTED,
                ContactsContract.PhoneLookup.LOOKUP_KEY};
        String selection = ContactsContract.PhoneLookup.IN_VISIBLE_GROUP + " = ?";
        String selectionArgs[] = {"1"};
        String sortOrder = ContactsContract.PhoneLookup.DISPLAY_NAME;

        Cursor people = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projection, selection, selectionArgs, sortOrder);

        while(people.moveToNext()) {
            String name = people.getString(
                    people.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));

            long lastContacted = people.getLong(
                    people.getColumnIndex(ContactsContract.PhoneLookup.LAST_TIME_CONTACTED));

            String lookupKey = people.getString(
                    people.getColumnIndex(ContactsContract.PhoneLookup.LOOKUP_KEY));

            people.getColumnNames();

            contacts.add(
                    new Contact(lookupKey, name, lastContacted));
        }
        return contacts;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        contacts = getContacts();
        arrayAdapter = new ArrayAdapter<Contact>(this, R.layout.item, R.id.helloText, contacts);
       // flingContainer = new SwipeFlingAdapterView( this.getApplicationContext());
        flingContainer.setAdapter(arrayAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                contacts.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

               //// ContactsContract.PhoneLookup._ID
                //getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,
                //        ContactsContract.RawContacts._ID+"=?", new String[]{id.toString()});
               // makeToast(MainActivity.this, "Left!");

                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, ((Contact) dataObject).getLookupKey());
                getContentResolver().delete(uri, null, null);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                /*
                // Ask for more data here
                al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
                */
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }

        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(MainActivity.this, "Clicked!");
            }
        });

    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.right)
    public void right() {
        /**
         * Trigger the right event manually.
         */
        flingContainer.getTopCardListener().selectRight();
    }

    @OnClick(R.id.left)
    public void left() {
        flingContainer.getTopCardListener().selectLeft();
    }
}

class Contact {
    private String lookupKey;
    private String name;
    private long lastContacted;

    Contact(String lookupKey, String name, long lastContacted){
        this.lookupKey = lookupKey;
        this.name = name;
        this.lastContacted = lastContacted;
    }

    String getLookupKey(){
            return lookupKey;
        }

    public String toString() {
        String string = name;
        if(lastContacted > 0){
            long daysAgo = TimeUnit.MILLISECONDS.toDays(
                    System.currentTimeMillis() - lastContacted);
            string +=  "\n Last called " + "\n" + daysAgo + " days ago";
        }
        else{
            string += "\n Last called " + "\n" + " never";
        }
        return string;
    }

}