# FitConnect

> Stay Fit, Wherever You Go.

![Language](https://img.shields.io/badge/Language-Java-blue.svg)
![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

## Project Overview

FitConnect is a specialized mobile fitness application designed for the **Mobile Systems (MobSys 2025)** course at Hochschule Schmalkalden. The project is built as a solution for frequent travelers, digital nomads, and anyone with a dynamic lifestyle who struggles to maintain a consistent fitness routine.

The application serves as a portable fitness hub, empowering users to find hyper-local workout opportunities and connect with a temporary fitness community in any new city. It integrates multiple hardware sensors and modern Android development practices to create a polished, functional, and user-centric experience.

## Key Features

### 1. Gamified Daily Step Challenge
*   **Sensor Fusion:** Combines the **Step Counter** to track progress towards a daily goal (e.g., 1000 steps) and the **Accelerometer** to detect a "shake" gesture.
*   **Interactive Reward System:** Upon reaching the step goal, the UI updates, prompting the user to shake their phone to reveal a reward (e.g., "Free 1-Day Gym Pass"), creating an engaging and gamified user experience.

### 2. Smart Gym Finder
*   **Live Distance Calculation:** Utilizes the device's **GPS** to get the user's real-time location.
*   **Dynamic & Sorted List:** Calculates the live distance to a curated list of local gyms and displays them in a sorted list from nearest to farthest, demonstrating a practical use of sensor data.
*   **Robust Permissions:** Implements the full, modern runtime permission model to request location access from the user.

### 3. Dynamic & Persistent Buddy System
*   **User Registration:** Allows new users to create a profile by entering their name and workout interests.
*   **Advanced Data Transfer:** Passes entire `Buddy` objects (as a `Serializable`) between activities, a more organized and scalable approach than passing simple data types.
*   **Permanent Data Storage:** New user profiles are saved permanently on the device using **SharedPreferences** and the **GSON library** for object serialization. This ensures user data is never lost when the app closes.

### 4. BMI Calculator with Custom View
*   **Custom `BmiGaugeView`:** Features a completely custom-drawn view that provides a visually intuitive gauge to display the calculated BMI result. This demonstrates an understanding of custom UI components beyond standard widgets.

## Technology Stack

*   **Language:** Java
*   **Platform:** Android Native (API Level 32)
*   **IDE:** Android Studio
*   **Key Libraries:**
    *   `androidx.appcompat:appcompat` & `androidx.cardview:cardview` for UI components.
    *   `com.google.code.gson:gson` for robust object serialization.
*   **Data Storage:** SharedPreferences
*   **Version Control:** Git & GitHub

## Setup

1.  Clone the repository:
    ```bash
    git clone https://github.com/YourUsername/FitConnect-Android-Project.git
    ```
2.  Open the project in Android Studio.
3.  Build and run the application.

---

This project was submitted in fulfillment of the requirements for the **Mobile Systems (MobSys 2025)** course, taught by **Dr.-Ing. David Sommer**.
