package com.example.shruthi.newsapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shruthi on 7/28/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsItrems.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_NewsItem.TABLE_NAME + " ("+

                Contract.TABLE_NewsItem.COLUMN_NAME_TITLE + " TEXT NOT NULL , " +
                Contract.TABLE_NewsItem.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL , " +
                Contract.TABLE_NewsItem.COLUMN_NAME_URL_TO_IMAGE + " TEXT NOT NULL , " +
                Contract.TABLE_NewsItem.COLUMN_NAME_URL + " TEXT NOT NULL , "+
                Contract.TABLE_NewsItem.COLUMN_NAME_AUTHOR + " TEXT NOT NULL , " +
                Contract.TABLE_NewsItem.COLUMN_NAME_PUBLISHED_At + " TEXT NOT NULL  " + "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + Contract.TABLE_NewsItem.TABLE_NAME + " if exists;");
    }
}