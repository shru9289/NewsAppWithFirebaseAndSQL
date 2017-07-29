package com.example.shruthi.newsapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.shruthi.newsapp.Model.NewsItem;
import com.example.shruthi.newsapp.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shruthi on 7/28/2017.
 */


// Shruthi -- This refresh task class is implemented to demonstrate refresh button , when refresh is clicked new news are added
public class RefreshTasks {

    public static final String ACTION_REFRESH = "refresh";


    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.makeURL();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {

            //delete all articles before inserting
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);

            //loop through all news items and insert them
            DatabaseUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }
}
