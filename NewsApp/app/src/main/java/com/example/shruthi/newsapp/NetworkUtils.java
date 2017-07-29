package com.example.shruthi.newsapp;

import android.net.Uri;
import android.util.Log;

import com.example.shruthi.newsapp.Model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class NetworkUtils {

    public static final String GITHUB_BASE_URL = "https://newsapi.org/v1/articles?";
    public static final String PARAM_QUERY = "source";
    public static final String PARAM_SORT = "sortBy";
    public static final String API_KEY = "apiKey";
    public static final String TAG = "NetworkUtils";
    // TODO insert api key here
    final static String KEY = "";

    public static URL makeURL() {
        Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, "the-next-web")
                .appendQueryParameter(PARAM_SORT, "latest")
                .appendQueryParameter(API_KEY, KEY)
                .build();

        URL url = null;
        try {
            String urlString = uri.toString();
            Log.d(TAG, "Url: " + urlString);
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");
            return (input.hasNext()) ? input.next() : null;

        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {

        ArrayList<NewsItem> results = new ArrayList<>();
        JSONObject resultsJson = new JSONObject(json);
        JSONArray arrayOfArticles = resultsJson.getJSONArray("articles");

        for (int i = 0; i < arrayOfArticles.length(); i++) {

            JSONObject item = arrayOfArticles.getJSONObject(i);
            String title = item.getString("title");
            String description = item.getString("description");
            String img_Url = item.getString("urlToImage");
            String url = item.getString("url");
            String author = item.getString("author");
            String publishedAt = item.getString("publishedAt");
            ;

            NewsItem ni = new NewsItem(title,  description, img_Url, url, author, publishedAt);

            results.add(ni);

        }

        return results;

    }
}