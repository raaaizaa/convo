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
    private Context context;
    private List<post> posts;
    private Integer notificationCounter;

    public post_adapter(List<post> posts, Context context, Integer notificationCounter) {
        this.posts = posts;
        this.context = context;
        this.notificationCounter = notificationCounter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextview, usernameTextview, titleTextview, bodyTextview;
        private ImageView commentButton, likeButton, saveButton;
        private LinearLayout userContainer;

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

            startDetailedPost(userId, personName, username, title, body, notificationCounter);
        });

        holder.userContainer.setOnClickListener(e -> {
            String userId = String.valueOf(post.getUserId());
            String personName = getPersonName(post.getUserId());
            String username = getUsername(post.getUserId());

            startProfile(userId, personName, username, notificationCounter);
        });

        holder.commentButton.setOnClickListener(e -> showToast("You're not logged in!"));
        holder.likeButton.setOnClickListener(e -> showToast("You're not logged in!"));
        holder.saveButton.setOnClickListener(e -> showToast("You're not logged in!"));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private void startDetailedPost(String userId, String personName, String username, String title, String body, Integer notificationCounter) {
        Intent intent = new Intent(context, detailed_post.class);
        intent.putExtra("userId", userId);
        intent.putExtra("personName", personName);
        intent.putExtra("username", username);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        intent.putExtra("notificationCounter", notificationCounter);
        context.startActivity(intent);
    }

    private void startProfile(String userId, String personName, String username, Integer notificationCounter){
        Intent intent = new Intent(context, profile.class);
        intent.putExtra("userId", userId);
        intent.putExtra("personName", personName);
        intent.putExtra("username", username);
        intent.putExtra("notificationCounter", notificationCounter);
        context.startActivity(intent);
    }

    private String getPersonName(Integer userId) {
        return "user " + userId;
    }

    private String getUsername(Integer userId) {
        return "@user" + userId;
    }

    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
