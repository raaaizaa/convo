package com.example.convo_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convo_app.R;
import com.example.convo_app.activities.detailed_post;
import com.example.convo_app.activities.profile;
import com.example.convo_app.models.post;

import java.util.List;

public class post_adapter extends RecyclerView.Adapter<post_adapter.ViewHolder> {
    private final Context context;
    private final List<post> posts;

    public post_adapter(List<post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextview, usernameTextview, titleTextview, bodyTextview;
        private final ImageView commentButton, likeButton, saveButton;
        private final LinearLayout userContainer;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameTextview = view.findViewById(R.id.post_person_name);
            usernameTextview = view.findViewById(R.id.post_username);
            titleTextview = view.findViewById(R.id.post_title);
            bodyTextview = view.findViewById(R.id.post_body);
            userContainer = view.findViewById(R.id.user_container);
            commentButton = view.findViewById(R.id.comment_button);
            likeButton = view.findViewById(R.id.like_button);
            saveButton = view.findViewById(R.id.save_button);
        }
    }

    @NonNull
    @Override
    public post_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull post_adapter.ViewHolder holder, int position) {
        post post = posts.get(position);
        holder.nameTextview.setText(getPersonName(post.getUserId()));
        holder.usernameTextview.setText(getUsername(post.getUserId()));
        holder.titleTextview.setText(post.getTitle());
        holder.bodyTextview.setText(post.getBody());

        holder.itemView.setOnClickListener(e -> {
            String userId = String.valueOf(post.getUserId());
            String personName = getPersonName(post.getUserId());
            String username = getUsername(post.getUserId());
            String title = post.getTitle();
            String body = post.getBody();

            startDetailedPost(userId, personName, username, title, body);
        });

        holder.userContainer.setOnClickListener(e -> {
            String userId = String.valueOf(post.getUserId());
            String personName = getPersonName(post.getUserId());
            String username = getUsername(post.getUserId());

            startProfile(userId, personName, username);
        });

        holder.commentButton.setOnClickListener(e -> showToast());
        holder.likeButton.setOnClickListener(e -> showToast());
        holder.saveButton.setOnClickListener(e -> showToast());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private void startDetailedPost(String userId, String personName, String username, String title, String body) {
        Intent intent = new Intent(context, detailed_post.class);
        intent.putExtra("userId", userId);
        intent.putExtra("personName", personName);
        intent.putExtra("username", username);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        context.startActivity(intent);
    }

    private void startProfile(String userId, String personName, String username) {
        Intent intent = new Intent(context, profile.class);
        intent.putExtra("userId", userId);
        intent.putExtra("personName", personName);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    private String getPersonName(Integer userId) {
        return "user " + userId;
    }

    private String getUsername(Integer userId) {
        return "@user" + userId;
    }

    private void showToast() {
        Toast.makeText(context, "You're not logged in!", Toast.LENGTH_SHORT).show();
    }
}
