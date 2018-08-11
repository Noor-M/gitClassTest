package com.example.noor.guardian;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    ListView listView;
    public static final String base_url = "https://content.guardianapis.com/search?api-key=f93aca36-2803-498b-afaa-32951e857f4a&show-fields=thumbnail,byline";
    ArrayList<News> news;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_of_news);
        textView=findViewById(R.id.value_tv);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader loader = loaderManager.getLoader(0);
        if( loader == null) {
            loaderManager.initLoader(0, null, this).forceLoad();
        } else
        {
            loaderManager.restartLoader(0, null, this).forceLoad();

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getItemAtPosition(position);
                Uri webPage = Uri.parse(news.getUrl());
                Intent myIntent = new Intent(Intent.ACTION_VIEW, webPage);
                startActivity(myIntent);


            }
        });


        if (!isConnected()) {
            Toast.makeText(this, "no connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "happy reading", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_menu:
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);

    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @NonNull

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String section = sharedPreferences.getString(getString(R.string.preference_key), "sport");

        Uri baseUri = Uri.parse(base_url);

        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("q", section);

        builder.build();

        return new NewsAsyncTask(this,builder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {
        if (data != null) {
            NewsAdapter newsAdapter = new NewsAdapter(this, R.layout.list_view, data);
            listView.setAdapter(newsAdapter);
            news = data;
        } else {

            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.tv);
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {

    }
}
