package com.example.ednunez.myapplication;

import android.graphics.Bitmap;

import java.util.concurrent.TimeUnit;

/**
 * Created by ednunez on 6/10/2015.
 */

class Contact {
    private String lookupKey;
    private String name;
    private long lastContacted;

    public Bitmap getPhoto() {
        return photo;
    }

    private Bitmap photo;

    Contact(String lookupKey, String name, long lastContacted, Bitmap photo) {
        this.lookupKey = lookupKey;
        this.name = name;
        this.lastContacted = lastContacted;
        this.photo = photo;
    }

    String getLookupKey() {
        return lookupKey;
    }

    public long getLastContacted() {
        return lastContacted;
    }

    public String getName() {

        return name;
    }

    public String toString() {
        String string = name;
        long daysAgo = TimeUnit.MILLISECONDS.toDays(
                System.currentTimeMillis() - lastContacted);
        string += "\n Last called " + "\n" + daysAgo + " days ago";
        return string;
    }

}