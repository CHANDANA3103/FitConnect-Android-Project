package com.example.fitconnect;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class BuddyListActivity extends AppCompatActivity {

    private ListView buddyListView;
    private ArrayList<Buddy> buddyList;
    private BuddyDataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy_list);

        dataHelper = new BuddyDataHelper(this);

        // Attempt to load the saved list of Buddy objects.
        buddyList = dataHelper.loadBuddies();

        // If no list was saved (app has never been run), create a NEW EMPTY LIST.
        if (buddyList == null) {
            buddyList = new ArrayList<>();
        }

        // Check for a new Buddy object from the RegistrationActivity.
        Intent intent = getIntent();
        if (intent.hasExtra("NEW_BUDDY_OBJECT")) {
            Buddy newBuddy = (Buddy) intent.getSerializableExtra("NEW_BUDDY_OBJECT");
            if (newBuddy != null) {
                buddyList.add(newBuddy);
                // Save the updated list (with the new member) to permanent storage.
                dataHelper.saveBuddies(buddyList);
                Toast.makeText(this, "Welcome, " + newBuddy.getName() + "!", Toast.LENGTH_LONG).show();
            }
        }

        buddyListView = findViewById(R.id.lv_buddy_list);
        ArrayAdapter<Buddy> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                buddyList
        );
        buddyListView.setAdapter(adapter);

        buddyListView.setOnItemClickListener((parent, view, position, id) -> {
            Buddy selectedBuddy = buddyList.get(position);
            Toast.makeText(this, "Connection request sent to " + selectedBuddy.getName(), Toast.LENGTH_SHORT).show();
        });
    }
}