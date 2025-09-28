package com.example.fitconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GymFinderActivity extends AppCompatActivity {

    private Button findGymsButton;
    private ListView gymListView;
    private LocationManager locationManager;
    private GymListAdapter adapter;
    private ArrayList<Gym> gymList;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_finder);

        findGymsButton = findViewById(R.id.btn_get_location);
        gymListView = findViewById(R.id.lv_gym_list);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Initialize our list of gyms with the newly added Joy Fitness.
        initializeGymList();

        // Setup the custom adapter with the initial list.
        adapter = new GymListAdapter(this, gymList);
        gymListView.setAdapter(adapter);

        findGymsButton.setOnClickListener(v -> getLocationAndShowGyms());
    }

    // This method now includes JOY Fitness.
    private void initializeGymList() {
        gymList = new ArrayList<>();

        // --- Gyms in and near Schmalkalden ---
        gymList.add(new Gym("JOY Fitness- & Gesundheitsclub", 50.7188, 10.4578)); // <-- ADDED THIS NEW GYM
        gymList.add(new Gym("Fitnessstudio Future-Fit", 50.7229, 10.4578));
        gymList.add(new Gym("Fitness- und Gesundheitsstudio K2", 50.7185, 10.4552));
        gymList.add(new Gym("Asporta Fitness & Health Club", 50.6861, 10.3644)); // In nearby Zella-Mehlis

        // --- Existing gyms in major German cities ---
        gymList.add(new Gym("McFIT Berlin", 52.5162, 13.3777));
        gymList.add(new Gym("FitX Munich", 48.1372, 11.5755));
        gymList.add(new Gym("SuperFit Hamburg", 53.5511, 9.9937));
        gymList.add(new Gym("Holmes Place Cologne", 50.9375, 6.9603));
        gymList.add(new Gym("Prime Time Fitness Frankfurt", 50.1109, 8.6821));
    }

    private void getLocationAndShowGyms() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Getting your location...", Toast.LENGTH_SHORT).show();
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(Location userLocation) {
                    Toast.makeText(GymFinderActivity.this, "Location found! Calculating distances.", Toast.LENGTH_SHORT).show();
                    updateDistancesAndSort(userLocation);
                }
            }, null);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void updateDistancesAndSort(Location userLocation) {
        // Loop through each gym in our list.
        for (Gym gym : gymList) {
            // Use the built-in 'distanceTo' method to calculate the distance in meters.
            float distance = userLocation.distanceTo(gym.getLocation());
            gym.setDistance(distance);
        }

        // Sort the list based on the newly calculated distances, from closest to farthest.
        Collections.sort(gymList, Comparator.comparing(Gym::getDistance));

        // Notify the adapter that the data has changed, so it redraws the list.
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocationAndShowGyms();
        } else {
            Toast.makeText(this, "Location permission is required to find nearby gyms.", Toast.LENGTH_LONG).show();
        }
    }
}