package com.example.fitconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText interestEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameEditText = findViewById(R.id.et_reg_name);
        interestEditText = findViewById(R.id.et_reg_interest);
        registerButton = findViewById(R.id.btn_register);

        registerButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String interest = interestEditText.getText().toString().trim();

            if (!name.isEmpty() && !interest.isEmpty()) {
                // 1. Create a new Buddy object with the user's data.
                Buddy newBuddy = new Buddy(name, interest);

                // 2. Create the Intent.
                Intent intent = new Intent(RegistrationActivity.this, BuddyListActivity.class);

                // 3. Put the ENTIRE Buddy object into the Intent. This is the improved part.
                intent.putExtra("NEW_BUDDY_OBJECT", newBuddy);

                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}