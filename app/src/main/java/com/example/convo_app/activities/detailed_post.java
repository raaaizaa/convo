package com.example.convo_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.convo_app.R;

public class detailed_post extends AppCompatActivity {
    private ImageView backButton, commentButton, likeButton, saveButton;
    private TextView nameTextview, usernameTextview, titleTextview, bodyTextview;
    private LinearLayout userContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);

        initialize();
    }

    private void initialize() {
        backButton = findViewById(R.id.back_button);
        nameTextview = findViewById(R.id.post_person_name);
        usernameTextview = findViewById(R.id.post_username);
        titleTextview = findViewById(R.id.post_title);
        bodyTextview = findViewById(R.id.post_body);
        userContainer = findViewById(R.id.user_container);
        commentButton = findViewById(R.id.comment_button);
        likeButton = findViewById(R.id.like_button);
        saveButton = findViewById(R.id.save_button);

        String userId = getIntent().getStringExtra("userId");
        String personName = getIntent().getStringExtra("personName");
        String username = getIntent().getStringExtra("username");
        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");

        setListener(userId, personName, username);
        setContent(personName, username, title, body);
    }

    private void setListener(String userId, String personName, String username) {
        backButton.setOnClickListener(e -> finish());

        userContainer.setOnClickListener(e -> {
            startProfile(userId, personName, username);
        });

        commentButton.setOnClickListener(e -> showToast("You're not logged in!"));
        likeButton.setOnClickListener(e -> showToast("You're not logged in!"));
        saveButton.setOnClickListener(e -> showToast("You're not logged in!"));
    }

    private void startProfile(String userId, String personName, String username) {
        Intent intent = new Intent(this, profile.class);
        intent.putExtra("userId", userId);
        intent.putExtra("personName", personName);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void setContent(String personName, String username, String title, String body){
        nameTextview.setText(personName);
        usernameTextview.setText(username);
        titleTextview.setText(title);
        bodyTextview.setText(body);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}