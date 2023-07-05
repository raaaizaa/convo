package com.example.convo_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convo_app.R;
import com.example.convo_app.models.post;

import java.util.List;

public class post_adapter extends RecyclerView.Adapter<post_adapter.ViewHolder> {
    private Context context;
    private List<post> posts;


    public post_adapter(List<post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, username, title, body;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.post_person_name);
            username = view.findViewById(R.id.post_username);
            title = view.findViewById(R.id.post_title);
            body = view.findViewById(R.id.post_body);
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
        holder.name.setText(getPersonName(post.getUserId()));
        holder.username.setText(getUsername(post.getUserId()));
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private String getPersonName(Integer userId) {
        return "user " + userId;
    }

    private String getUsername(Integer userId) {
        return "@user" + userId;
    }
}
