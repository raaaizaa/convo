package com.example.convo_app.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.convo_app.R;

public class fragment_notification extends Fragment {
    private Context context;
    private View view;
    Integer notificationCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        this.context = getContext();

        Bundle args = getArguments();

        if(args != null){
            notificationCount = args.getInt("notificationCount");
        }

        showToast(notificationCount);
        return view;
    }

    private void showToast(Integer notificationCount){
        Toast.makeText(context, "number of notification = " + notificationCount, Toast.LENGTH_SHORT).show();
    }
}