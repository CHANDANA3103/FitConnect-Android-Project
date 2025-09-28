package com.example.fitconnect; // Make sure this matches your package name

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BuddyProfileActivity extends AppCompatActivity {

    // 1. Declare our UI elements
    TextView buddyNameTextView;
    Button replyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy_profile);

        // 2. Connect them to the XML layout
        buddyNameTextView = findViewById(R.id.tv_buddy_name_details);
        replyButton = findViewById(R.id.btn_reply);

        // 3. Get the Intent (the messenger) that started this activity
        Intent intent = getIntent();
        // Get the data that was attached to the messenger with the key "BUDDY_NAME"
        String buddyName = intent.getStringExtra("BUDDY_NAME");

        // 4. Set the buddy's name in our TextView
        buddyNameTextView.setText(buddyName);

        // 5. Set a click listener for our reply button
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new empty messenger to send a result back
                Intent replyIntent = new Intent();
                // Put our reply message in the messenger
                replyIntent.putExtra("REPLY_MESSAGE", "Connection request sent to " + buddyName);
                // Set the result to "OK" and include our reply messenger
                setResult(RESULT_OK, replyIntent);
                // Close this screen and go back to the previous one
                finish();
            }
        });
    }
}