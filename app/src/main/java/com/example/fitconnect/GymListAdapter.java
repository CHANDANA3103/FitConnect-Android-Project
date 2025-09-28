package com.example.fitconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

// We extend ArrayAdapter and tell it we're working with Gym objects.
public class GymListAdapter extends ArrayAdapter<Gym> {

    public GymListAdapter(@NonNull Context context, @NonNull List<Gym> gymList) {
        super(context, 0, gymList);
    }

    // This method is called for each item in the list.
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // If we don't have a recycled view, inflate a new one from our custom layout file.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_gym, parent, false);
        }

        // Get the Gym object for the current position.
        Gym currentGym = getItem(position);

        // Find the TextViews inside our custom layout.
        TextView gymNameTextView = convertView.findViewById(R.id.tv_gym_name);
        TextView gymDistanceTextView = convertView.findViewById(R.id.tv_gym_distance);

        // If the gym object exists, populate the TextViews with its data.
        if (currentGym != null) {
            gymNameTextView.setText(currentGym.getName());

            // Convert distance from meters to kilometers and format it nicely.
            float distanceInKm = currentGym.getDistance() / 1000;
            gymDistanceTextView.setText(String.format("%.1f km", distanceInKm));
        }

        // Return the finished view to be displayed.
        return convertView;
    }
}