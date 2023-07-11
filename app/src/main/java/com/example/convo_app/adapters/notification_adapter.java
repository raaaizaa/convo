package com.example.convo_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convo_app.R;

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.ViewHolder> {
    private final Context context;
    private final int itemCount;

    public notification_adapter(Context context, int itemCount) {
        this.context = context;
        this.itemCount = itemCount;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View view) {
            super(view);
        }
    }

    @NonNull
    @Override
    public notification_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notification_adapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(e -> showToast());
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public void showToast() {
        Toast.makeText(context, "Sorry, this service currently not available!", Toast.LENGTH_SHORT).show();
    }
}
