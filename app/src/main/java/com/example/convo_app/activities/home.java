package com.example.convo_app.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.convo_app.R;
import com.example.convo_app.fragments.fragment_home;
import com.example.convo_app.fragments.fragment_notification;
import com.example.convo_app.fragments.fragment_profile;
import com.example.convo_app.utils.counter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        refreshingNotification();
        firebaseSetup();
        initialize();
    }

    private void initialize() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

        setListener();
    }

    @SuppressLint("NonConstantResourceId")
    private void setListener() {

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment_home homeFragment = new fragment_home();
                    replaceFragment(homeFragment);
                    return true;

                case R.id.navigation_notification:
                    fragment_notification notificationFragment = new fragment_notification();
                    replaceFragment(notificationFragment);
                    return true;

                case R.id.navigation_profile:
                    fragment_profile profileFragment = new fragment_profile();
                    replaceFragment(profileFragment);
                    return true;
            }

            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void firebaseSetup() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Log.d(TAG, "Token: " + token);
                });

        FirebaseMessaging.getInstance().subscribeToTopic("loggedin")
                .addOnCompleteListener(task -> {
                    String message = "done";
                    if (!task.isSuccessful()) {
                        message = "failed";
                    }
                });
    }

    private void refreshingNotification() {
        SharedPreferences sharedPref = this.getSharedPreferences(counter.PREFS_KEY, Context.MODE_PRIVATE);
        sharedPref.edit().putInt(counter.COUNT, 0).apply();
        int refreshedValue = sharedPref.getInt(counter.COUNT, 0);
    }

}