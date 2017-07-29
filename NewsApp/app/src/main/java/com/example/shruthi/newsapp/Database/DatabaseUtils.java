package com.example.shruthi.newsapp.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static com.example.shruthi.newsapp.Database.Contract.TABLE_NewsItem.COLUMN_NAME_DESCRIPTION;
import static com.example.shruthi.newsapp.Database.Contract.TABLE_NewsItem.COLUMN_NAME_AUTHOR;
import static com.example.shruthi.newsapp.Database.Contract.TABLE_NewsItem.COLUMN_NAME_URL_TO_IMAGE;
import static com.example.shruthi.newsapp.Database.Contract.TABLE_NewsItem.COLUMN_NAME_PUBLISHED_At;
import static com.example.shruthi.newsapp.Database.Contract.TABLE_NewsItem.COLUMN_NAME_TITLE;
import static com.example.shruthi.newsapp.Database.Contract.TABLE_NewsItem.COLUMN_NAME_URL;
import static com.example.shruthi.newsapp.Database.Contract.TABLE_NewsItem.TABLE_NAME;

import com.example.shruthi.newsapp.Model.NewsItem;

import java.util.ArrayList;

/**
 * Created by Shruthi on 7/28/2017.
 */
public class DatabaseUtils {




    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> newsList) {

        db.beginTransaction();
        try {
            for (NewsItem a : newsList) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, a.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, a.getDescription());
                cv.put(COLUMN_NAME_URL_TO_IMAGE, a.getImageUrl());
                cv.put(COLUMN_NAME_URL,a.getUrl());
                cv.put(COLUMN_NAME_AUTHOR,a.getAuthor());
                cv.put(COLUMN_NAME_PUBLISHED_At,a.getPublished());

                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_At + " DESC"
        );
        return cursor;

    }


    public static Cursor getALLin1(SQLiteDatabase db) {

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_At + " DESC"
        );
        return cursor;

    }
}