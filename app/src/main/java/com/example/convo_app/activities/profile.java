package com.example.convo_app.activities;

import static com.example.convo_app.utils.counter.COUNT;
import static com.example.convo_app.utils.counter.PREFS_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.convo_app.R;
import com.example.convo_app.adapters.post_adapter;
import com.example.convo_app.models.post;
import com.example.convo_app.utils.post_database_helper;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {
    private post_database_helper postDb;
    private List<post> posts;
    private TextView personNameTextview, usernameTextview, userIdTextview;
    private RecyclerView postRV;
    private Button followButton;
    private post_adapter adapter;
    private ImageView backButton;
    private String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 100;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initialize();
    }

    public void initialize() {
        personNameTextview = findViewById(R.id.profile_person_name);
        usernameTextview = findViewById(R.id.profile_username);
        userIdTextview = findViewById(R.id.profile_userid);
        postRV = findViewById(R.id.post_recyclerview);
        followButton = findViewById(R.id.follow_button);
        backButton = findViewById(R.id.back_button);

        String userId = getIntent().getStringExtra("userId");
        String personName = getIntent().getStringExtra("personName");
        String username = getIntent().getStringExtra("username");

        setListener();
        setDetails(personName, username, userId);
        getPosts(Integer.parseInt(userId));
    }

    private void setListener() {
        backButton.setOnClickListener(e -> finish());

        followButton.setOnClickListener(e -> {
            showToast("Clicked");
            showNotification();
        });

    }

    @SuppressLint("SetTextI18n")
    private void setDetails(String personName, String username, String userId) {
        personNameTextview.setText(personName);
        usernameTextview.setText(username);
        userIdTextview.setText("User ID: " + userId);
    }

    private void getPosts(Integer userId) {
        postDb = new post_database_helper(this);
        posts = new ArrayList<>();
        posts = postDb.viewPost(userId);
        setRecyclerview(posts, this);
    }

    private void setRecyclerview(List<post> posts, Context context) {
        adapter = new post_adapter(posts, context);
        postRV.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        postRV.setLayoutManager(layoutManager);
    }

    private void showNotification(){
        NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.baseline_notifications_active_24)
                    .setContentText("You're not logged in!")
                    .setChannelId(CHANNEL_ID)
                    .build();
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH));
        }else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.baseline_notifications_active_24)
                    .setContentText("You're not logged in!")
                    .build();
        }

        notificationManager.notify(NOTIFICATION_ID, notification);
        countNotification();
    }

    private void countNotification() {
        final SharedPreferences sharedPref = this.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        final int count = sharedPref.getInt(COUNT, 0);

        int value = sharedPref.getInt(COUNT, 0);
        Log.i("PROFILE SEBELUM DITAMBAH", String.valueOf(value));

        sharedPref.edit().putInt(COUNT, (value+1)).apply();
        int refreshedValue = sharedPref.getInt(COUNT, 0);
        Log.i("PROFILE SETELAH DITAMBAH", String.valueOf(refreshedValue));
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}