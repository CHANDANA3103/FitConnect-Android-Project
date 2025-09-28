package com.example.fitconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView stepProgressTextView, stepLabelTextView;
    private CardView stepCardView;
    private ImageView footprintIcon;

    // Sensor-related variables
    private SensorManager sensorManager;
    private Sensor stepCounterSensor, accelerometerSensor;
    private boolean goalReached = false;
    private boolean rewardClaimed = false;

    // Constants for our logic
    private static final int DAILY_GOAL = 1000;
    private static final int PERMISSION_CODE = 100;
    private static final float SHAKE_THRESHOLD = 12.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        stepProgressTextView = findViewById(R.id.tv_step_progress);
        stepLabelTextView = findViewById(R.id.tv_step_label);
        stepCardView = findViewById(R.id.cv_step_card);
        footprintIcon = findViewById(R.id.iv_footprint_icon);
        findViewById(R.id.btn_find_gym).setOnClickListener(v -> startActivity(new Intent(this, GymFinderActivity.class)));
        findViewById(R.id.btn_find_buddy).setOnClickListener(v -> startActivity(new Intent(this, RegistrationActivity.class)));
        findViewById(R.id.btn_bmi_calculator).setOnClickListener(v -> startActivity(new Intent(this, BmiCalculatorActivity.class)));

        // Initialize the SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            registerSensors();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, PERMISSION_CODE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int currentSteps = (int) event.values[0];
            updateStepCountUI(currentSteps);

            if (currentSteps >= DAILY_GOAL && !goalReached) {
                goalReached = true;
                rewardClaimed = false;
                updateGoalReachedUI();
            }
        }
        else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (goalReached && !rewardClaimed) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                double acceleration = Math.sqrt(x * x + y * y + z * z);
                if (acceleration > SHAKE_THRESHOLD) {
                    rewardClaimed = true;
                    showRewardDialog();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registerSensors();
        } else {
            Toast.makeText(this, "Permission is required to count steps.", Toast.LENGTH_LONG).show();
            updateStepCountUI(0);
        }
    }

    private void registerSensors() {
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
        }
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void updateStepCountUI(int steps) {
        stepProgressTextView.setText(steps + " / " + DAILY_GOAL);
    }

    // --- THIS IS THE UPDATED METHOD ---
    private void updateGoalReachedUI() {
        footprintIcon.setVisibility(View.GONE); // Hide the footprint icon to make space
        // Change to a subtle, theme-appropriate light gray
        stepCardView.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
        // Use shorter, clearer text that will align better
        stepProgressTextView.setText("Goal!");
        stepProgressTextView.setTextSize(40); // Slightly larger for impact
        stepLabelTextView.setText("Shake to Claim");
        stepLabelTextView.setTextSize(16); // Make this text a bit smaller
    }

    private void showRewardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ImageView rewardImageView = new ImageView(this);
        rewardImageView.setImageResource(R.drawable.reward_card);
        builder.setView(rewardImageView);

        builder.setPositiveButton("Awesome!", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}