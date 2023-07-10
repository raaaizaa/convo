package com.example.convo_app.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.convo_app.R;
import com.example.convo_app.fragments.fragment_home;
import com.example.convo_app.fragments.fragment_notification;
import com.example.convo_app.fragments.fragment_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(TAG, "Token: " + token);
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("loggedin")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String message = "done";
                        if (!task.isSuccessful()) {
                            message = "failed";
                        }
                    }
                });

        initialize();
    }

    private void initialize() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        Integer notificationCount = getIntent().getIntExtra("notificationCounter", -1);

        setListener(notificationCount);
    }

    @SuppressLint("NonConstantResourceId")
    private void setListener(Integer notificationCount) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment_home homeFragment = new fragment_home();
                    replaceFragment(homeFragment, notificationCount);
                    return true;

                case R.id.navigation_notification:
                    fragment_notification notificationFragment = new fragment_notification();
                    replaceFragment(notificationFragment, notificationCount);
                    return true;

                case R.id.navigation_profile:
                    fragment_profile profileFragment = new fragment_profile();
                    replaceFragment(profileFragment, notificationCount);
                    return true;
            }

            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void replaceFragment(Fragment fragment, Integer notificationCount) {
        Bundle args = new Bundle();
        args.putInt("notificationCount", notificationCount);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}