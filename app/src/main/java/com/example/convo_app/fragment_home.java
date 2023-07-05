package com.example.convo_app;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.convo_app.adapters.post_adapter;
import com.example.convo_app.models.post;
import com.example.convo_app.utils.post_database_helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragment_home extends Fragment {
    private View view;
    private Context context;
    private RecyclerView timelineRV;
    private post_database_helper postDb;
    private RequestQueue requestQueue;
    private String URL = "https://jsonplaceholder.typicode.com/posts";
    private post_adapter adapter;
    private List<post> posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        this.context = getContext();

        timelineRV = view.findViewById(R.id.timeline_recyclerview);
        fetch();
        return view;
    }

    private void initialize() {

    }

    private void fetch() {
        postDb = new post_database_helper(context);
        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("sampe sini", "sampe sini");
                try {
                    JSONArray jsonArray = response.getJSONArray("posts");
                    posts = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Integer userId = jsonObject.getInt("userId");
                        Integer id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String body = jsonObject.getString("body");

                        Log.i("ASD", userId + id + title + body);

                        insertToDatabase(userId, id, title, body);
                        post newPost = new post(userId, id, title, body);
                        posts.add(newPost);
                        setRecyclerview(posts, context);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
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