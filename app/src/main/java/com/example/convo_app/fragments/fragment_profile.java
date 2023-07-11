package com.example.convo_app.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.convo_app.R;
import com.example.convo_app.adapters.notification_adapter;

public class fragment_profile extends Fragment {
    private View view;
    private Context context;
    private Button loginButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.context = getContext();

        initialize();

        return view;
    }

    private void initialize() {
        loginButton = view.findViewById(R.id.login_button);

        setListener();
    }

    private void setListener() {
        loginButton.setOnClickListener(e -> showToast("Sorry, this service currently not available!"));
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}