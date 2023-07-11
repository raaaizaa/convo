package com.example.convo_app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
    private RecyclerView notificationRV;
    private notification_adapter adapter;
    private LinearLayout notificationDescription;
    public static String COUNT = "count";
    public static String PREFS_KEY = "com.example.convo-app";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        this.context = getContext();

        initialize();
        setRecyclerview(context);
        showToast();
        return view;
    }

    private void initialize() {
        notificationRV = view.findViewById(R.id.notification_recyclerview);
        notificationDescription = view.findViewById(R.id.notification_description);
    }

    private void setRecyclerview(Context context) {
        Integer itemCount = getNotificationCount();

        if(itemCount == 0){
            notificationDescription.setVisibility(View.VISIBLE);
        }
        else if(itemCount != 0) {
            notificationDescription.setVisibility(View.GONE);
            adapter = new notification_adapter(context, itemCount);
            notificationRV.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            notificationRV.setLayoutManager(layoutManager);
        }


    }

    private void showToast() {
//        Toast.makeText(context, "number of notification = " + counter, Toast.LENGTH_SHORT).show();
    }

    private int getNotificationCount() {
        final SharedPreferences prefs = getContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);

        int refreshedValue = prefs.getInt(COUNT, 0);
        Log.i("NOTIF SETELAH DITAMBAH", String.valueOf(refreshedValue));

        return refreshedValue;
    }


}