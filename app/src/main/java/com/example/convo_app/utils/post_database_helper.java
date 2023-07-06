package com.example.convo_app.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.convo_app.models.post;

import java.util.ArrayList;
import java.util.List;

public class post_database_helper extends SQLiteOpenHelper {
    public static final String POST_DB = "post.db";
    List<post> posts;

    public post_database_helper(Context context) {
        super(context, POST_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE post(id INTEGER PRIMARY KEY, userId INTEGER, title VARCHAR(64), body VARCHAR(256))";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS post";
        db.execSQL(dropQuery);
    }

    public void createPost(Integer id, Integer userId, String title, String body) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isPostExists = checkPost(id);

        if (!isPostExists) {
            ContentValues contentValues = inputPost(id, userId, title, body);

            long result = db.insert("post", null, contentValues);
            db.close();
        }
    }

    public boolean checkPost(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM post WHERE id = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public List<post> viewPost(Integer userId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM post WHERE userId = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        posts = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String body = cursor.getString(cursor.getColumnIndex("body"));

                post post = new post(userId, id, title, body);
                posts.add(post);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return posts;
    }

    private ContentValues inputPost(Integer id, Integer userId, String title, String body) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("userId", userId);
        contentValues.put("title", title);
        contentValues.put("body", body);

        return contentValues;
    }
}
