package com.example.convo_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.convo_app.R;

public class detailed_post extends AppCompatActivity {
    private ImageView backButton;
    private TextView nameTextview, usernameTextview, titleTextview, bodyTextview;


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

        String userId = getIntent().getStringExtra("userId");
        String personName = getIntent().getStringExtra("personName");
        String username = getIntent().getStringExtra("username");
        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");

        setListener();
        setContent(personName, username, title, body);
    }

    private void setListener() {
        backButton.setOnClickListener(e -> finish());
    }

    private void setContent(String personName, String username, String title, String body){
        nameTextview.setText(personName);
        usernameTextview.setText(username);
        titleTextview.setText(title);
        bodyTextview.setText(body);
    }
}