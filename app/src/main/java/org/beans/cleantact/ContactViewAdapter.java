package org.beans.cleantact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by ednunez on 8/12/2015.
 */
public class ContactViewAdapter extends ArrayAdapter {

    private ArrayList<Contact> contacts;

    public ContactViewAdapter(Context context, int contactViewResourceId, int resource, ArrayList<Contact> contacts) {
        super(context, contactViewResourceId, resource, contacts);
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contactview, parent, false);
        }
        Contact contact = contacts.get(position);
        TextView contactInfo = (TextView) convertView.findViewById(R.id.contact_info);
        ImageView contactPhoto = (ImageView) convertView.findViewById(R.id.contact_photo);
        contactInfo.setText(formatContactInfoText(contact));
        contactPhoto.setImageBitmap(contact.getPhoto());
        return convertView;
    }

    String formatContactInfoText(Contact contact){
        String name = contact.getName();
        long daysLastContacted = TimeUnit.MILLISECONDS.toDays(
                System.currentTimeMillis() - contact.getLastContacted());

        String contactInfoText = name + "\n Last called: \n";
        String timeUnit = null;
        long timeValue = 0;

        if(contact.getLastContacted() == 0)
            contactInfoText += "never";
        else if(daysLastContacted >= 730){
            timeUnit = "years";
            timeValue = daysLastContacted / 365;
        }
        else if(daysLastContacted >= 60){
            timeUnit = "months";
            timeValue = daysLastContacted / 30;
        }
        else if(daysLastContacted >= 14){
            timeUnit = "weeks";
            timeValue = daysLastContacted / 7;
        }
        else if(daysLastContacted >= 7){
            contactInfoText += "a week ago";
        }
        else  {
            contactInfoText += "within a week";
        }
        if(timeUnit != null)
            contactInfoText = name + "\n Last called: " + "\n" + timeValue + " " + timeUnit + " ago";

        return contactInfoText;
    }

}
