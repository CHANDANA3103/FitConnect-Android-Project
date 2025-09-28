package com.example.fitconnect;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

// This helper class uses the GSON library to save and load a list of Buddy objects.
public class BuddyDataHelper {

    private static final String PREFS_NAME = "FitConnectPrefs";
    private static final String BUDDIES_KEY = "BuddyObjectList";
    private SharedPreferences sharedPreferences;
    private Gson gson; // The GSON object for converting objects to/from text.

    public BuddyDataHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    /**
     * Saves the list of Buddy objects to persistent storage.
     * @param buddyList The ArrayList of Buddy objects to save.
     */
    public void saveBuddies(ArrayList<Buddy> buddyList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // GSON converts our list of Buddy objects into a single JSON string (text).
        String json = gson.toJson(buddyList);
        editor.putString(BUDDIES_KEY, json);
        editor.apply();
    }

    /**
     * Loads the list of Buddy objects from persistent storage.
     * @return The saved ArrayList of Buddy objects, or null if nothing is saved.
     */
    public ArrayList<Buddy> loadBuddies() {
        // Retrieve the saved JSON string from SharedPreferences.
        String json = sharedPreferences.getString(BUDDIES_KEY, null);
        if (json == null) {
            return null;
        }
        // Define the type of object we want to convert the JSON back into (an ArrayList of Buddy).
        Type type = new TypeToken<ArrayList<Buddy>>() {}.getType();
        // GSON converts the JSON string back into our list of Buddy objects.
        return gson.fromJson(json, type);
    }
}