package com.example.shruthi.newsapp.Database;

import android.provider.BaseColumns;

/**
 * Created by Shruthi on 7/28/2017.
 */


// Shruthi -- added new Database folder to implement SQL and this is a contract class
public class Contract {

    public class TABLE_NewsItem implements BaseColumns {

        public static final String TABLE_NAME = "newsItems";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_PUBLISHED_At = "publishedAt";


    }



}