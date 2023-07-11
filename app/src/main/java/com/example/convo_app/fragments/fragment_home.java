package com.example.convo_app.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.convo_app.R;
import com.example.convo_app.adapters.post_adapter;
import com.example.convo_app.models.post;
import com.example.convo_app.utils.post_database_helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragment_home extends Fragment {
    private post_database_helper postDb;
    private post_adapter adapter;
    private List<post> posts;
    private List<post> allPost;
    private View view;
    private Context context;
    private RecyclerView timelineRV;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private static final String URL = "https://jsonplaceholder.typicode.com/posts";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        this.context = getContext();

        initialize();
        checkDataFromDatabase();
        fetch();

        return view;
    }

    private void initialize() {
        timelineRV = view.findViewById(R.id.timeline_recyclerview);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    private void checkDataFromDatabase() {
        postDb = new post_database_helper(context);
        allPost = postDb.viewAllPost();

        if (allPost == null) {
            fetch();
        } else {
            progressBar.setVisibility(View.GONE);
            setRecyclerview(allPost, context);
        }
    }

    private void fetch() {
        requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            try {
                posts = new ArrayList<>();
                Log.i("tes", "sampe sini");
                progressBar.setVisibility(View.VISIBLE);
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);

                    Integer id = jsonObject.getInt("id");
                    Integer userId = jsonObject.getInt("userId");
                    String title = jsonObject.getString("title");
                    String body = jsonObject.getString("body");
                    Log.i("asd", userId + title + body);
                    insertToDatabase(userId, id, title, body);
                    post post = new post(userId, id, title, body);
                    posts.add(post);
                }
                progressBar.setVisibility(View.GONE);
                setRecyclerview(posts, context);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        requestQueue.add(request);
    }

    private void setRecyclerview(List<post> posts, Context context) {
        adapter = new post_adapter(posts, context);
        timelineRV.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        timelineRV.setLayoutManager(layoutManager);
    }

    private void insertToDatabase(Integer userId, Integer id, String title, String body) {
        postDb.createPost(id, userId, title, body);
    }
}