package com.adg.challenge_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.adg.challenge_1.adapter.ContactListAdapter;
import com.adg.challenge_1.model.Contact;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the reference to the RV
        contactList = findViewById(R.id.actMain_listContacts);

        // set the Layout Manager for the RV
        // using LinearLayout will order the items one by one
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        contactList.setLayoutManager(llm);

        // create a dummy data source
        ArrayList<Contact> contactListDS = new ArrayList<>();
        contactListDS.add(new Contact("Ana", "0333111111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Ada", "0333222111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Bogdan", "0333333111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Dan", "0333444111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Dumitru", "0333555111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("George", "0333666111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Zamfir", "0333777111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Ana2", "0333111111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Ada2", "0333222111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Bogdan2", "0333333111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Dan2", "0333444111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Dumitru2", "0333555111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("George2", "0333666111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Zamfir2", "0333777111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Ana3", "0333111111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Ada3", "0333222111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Bogdan3", "0333333111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Dan3", "0333444111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Dumitru3", "0333555111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("George3", "0333666111", R.drawable.ic_launcher_foreground));
        contactListDS.add(new Contact("Zamfir3", "0333777111", R.drawable.ic_launcher_foreground));

        contactListDS.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        ArrayList<Contact> contactListDSWithHeaders = new ArrayList<>();

        // adding headers
        if (contactListDS.size() > 0) {
            int i = 0;
            char lastChar = contactListDS.get(0).getName().charAt(0);

            contactListDSWithHeaders.add(i, new Contact(String.valueOf(lastChar), "x", null));
            i++;

            for (int j = 0; j < contactListDS.size(); j++) {
                char currentChar = contactListDS.get(j).getName().charAt(0);

                if (currentChar != lastChar) {
                    lastChar = currentChar;
                    contactListDSWithHeaders.add(i, new Contact(String.valueOf(lastChar), "x", null));
                    i++;
                }

                contactListDSWithHeaders.add(i, contactListDS.get(j));
                i++;
            }
        }

        // create adapter for the data source
        ContactListAdapter adapter = new ContactListAdapter(contactListDSWithHeaders);

        // set the adapter to the RV
        contactList.setAdapter(adapter);
    }
}