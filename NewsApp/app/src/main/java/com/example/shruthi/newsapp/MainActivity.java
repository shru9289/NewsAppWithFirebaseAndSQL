package com.example.shruthi.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.shruthi.newsapp.Database.Contract;
import com.example.shruthi.newsapp.Database.DBHelper;
import com.example.shruthi.newsapp.Database.DatabaseUtils;
import com.example.shruthi.newsapp.Database.RefreshTasks;


//Shruthi - LoadManager is implemented instead of Asynctask and used all its methods
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.NewsAdapterOnClickHandler{
    private ProgressBar progressDialog;


    private static final  int loaderID=1;

    static final String TAG = "mainactivity";


    private Cursor cursor;
    private SQLiteDatabase db;


    private android.support.v7.widget.RecyclerView RecyclerView;
    private NewsAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog= (ProgressBar) findViewById(R.id.progressBar);

        RecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.setLayoutManager(layoutManager);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        /* Shruthi--- if it's the first app run, call news API and insert results into db immediately
        instead of waiting 60 seconds for the job to do it because onstart will soon try to pull from an empty db and we do not
        want to do that
         */

        if (isFirst) {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(this);


    }

    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(loaderID, null, this).forceLoad();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Shruthi --//get all stories from db
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        Adapter = new NewsAdapter(cursor,this);
        RecyclerView.setAdapter(Adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.search) {
            //Shruthi --delete all from db, call news API, insert all results into db
            //refresh cursor to fetch all results from db
            load();
        }

        return true;
    }
    //Shruthi-- AsynctaskLoader
    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return  new AsyncTaskLoader<Void>(this) {


            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressDialog.setVisibility(View.VISIBLE);


            }
            @Override
            //Shruthi --delete all from db, call news API, insert all results into db
            public Void loadInBackground() {
                RefreshTasks.refreshArticles(MainActivity.this);
                return null;
            }

        };

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Void> loader, Void data) {

        progressDialog.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getALLin1(db);

        Adapter = new NewsAdapter(cursor, this);
        RecyclerView.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Void> loader) {

    }
//
    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItem.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}