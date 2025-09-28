package com.example.fitconnect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// This activity provides a tool to calculate Body Mass Index (BMI).
public class BmiCalculatorActivity extends AppCompatActivity {

    // UI elements for the BMI calculator.
    private EditText weightEditText;
    private EditText heightEditText;
    private Button calculateButton;
    // The custom view we created to display the BMI result visually.
    private BmiGaugeView bmiGaugeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        // Initialize UI elements.
        weightEditText = findViewById(R.id.et_weight);
        heightEditText = findViewById(R.id.et_height);
        calculateButton = findViewById(R.id.btn_calculate);
        bmiGaugeView = findViewById(R.id.bmi_gauge_view);

        // Set the click listener for the calculation button.
        calculateButton.setOnClickListener(v -> calculateAndShowBmi());
    }

    // This method handles the logic for calculating and displaying the BMI.
    private void calculateAndShowBmi() {
        // Get user input from EditTexts.
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();

        // Validate that both fields contain data.
        if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr);

            // Ensure height is not zero to avoid division errors.
            if (height > 0) {
                // The standard BMI formula: weight (kg) / (height (m) ^ 2).
                float bmi = weight / (height * height);
                // Send the calculated BMI value to our custom view, which will then redraw itself.
                bmiGaugeView.setBmi(bmi);
            }
        } else {
            // Show an error message if fields are empty.
            Toast.makeText(this, "Please enter both weight and height", Toast.LENGTH_SHORT).show();
        }
    }
}