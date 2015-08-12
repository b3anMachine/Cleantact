package com.example.ednunez.myapplication;

import java.util.concurrent.TimeUnit;

/**
 * Created by ednunez on 6/10/2015.
 */

class Contact {
    private String lookupKey;
    private String name;
    private long lastContacted;

    Contact(String lookupKey, String name, long lastContacted) {
        this.lookupKey = lookupKey;
        this.name = name;
        this.lastContacted = lastContacted;
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