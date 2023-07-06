package com.example.convo_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.convo_app.R;
import com.example.convo_app.fragments.fragment_home;
import com.example.convo_app.fragments.fragment_notification;
import com.example.convo_app.fragments.fragment_profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}