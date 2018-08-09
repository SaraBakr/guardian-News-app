package com.example.sara.news;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;


public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    ListView listView;

    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view_main);
        listView.setDivider(null);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, this).forceLoad();

    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {

        return new AsyncTaskLoaderClass(this, "https://content.guardianapis.com/search?api-key=cf5f341a-c255-40b8-9d29-10ff095e944f&q=books&show-fields=thumbnail,byline");
    }


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> news) {

        newsAdapter = new NewsAdapter(this, R.layout.list_model, news);
        listView.setAdapter(newsAdapter);


    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {

    }
}
