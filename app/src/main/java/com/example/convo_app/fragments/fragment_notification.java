package com.example.convo_app.fragments;

import static com.example.convo_app.utils.counter.COUNT;
import static com.example.convo_app.utils.counter.PREFS_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.convo_app.R;
import com.example.convo_app.adapters.notification_adapter;

public class fragment_notification extends Fragment {
    private Context context;
    private View view;
    private ConstraintLayout notificationDescription;
    private RecyclerView notificationRV;
    private notification_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        this.context = getContext();

        initialize();
        setRecyclerview(context);

        return view;
    }

    private void initialize() {
        notificationRV = view.findViewById(R.id.notification_recyclerview);
        notificationDescription = view.findViewById(R.id.notification_description);
    }

    private void setRecyclerview(Context context) {
        int itemCount = getNotificationCount();

        if (itemCount == 0) {
            notificationDescription.setVisibility(View.VISIBLE);
        } else {
            notificationDescription.setVisibility(View.GONE);
            adapter = new notification_adapter(context, itemCount);
            notificationRV.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            notificationRV.setLayoutManager(layoutManager);
        }
    }

    private int getNotificationCount() {
        final SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        return prefs.getInt(COUNT, 0);
    }
}