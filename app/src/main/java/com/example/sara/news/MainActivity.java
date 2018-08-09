package com.example.sara.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    NewsAdapter newsAdapter;
    TextView tv;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view_main);
        listView.setDivider(null);
        tv = findViewById(R.id.no_connection_tv);


        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, this).forceLoad();


        if (!isConnected()) {
            Toast.makeText(this, "you are not connected", Toast.LENGTH_LONG).show();
        }





        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.orange)
                .build();

        new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this).addDrawerItems(new PrimaryDrawerItem().withName("home").withIdentifier(0)
                , new PrimaryDrawerItem().withName("sport").withIdentifier(1),
                new PrimaryDrawerItem().withName("books").withIdentifier(2)).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                if (drawerItem.getIdentifier() == 1) {
                    Intent intent = new Intent(MainActivity.this, SportsActivity.class);
                    startActivity(intent);

                }
                if (drawerItem.getIdentifier() == 2) {
                    Intent intent = new Intent(MainActivity.this, BooksActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        })
                .build();



    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args)
    {
        return new AsyncTaskLoaderClass(this, "https://content.guardianapis.com/search?api-key=cf5f341a-c255-40b8-9d29-10ff095e944f&&show-fields=thumbnail,byline");
    }


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, final ArrayList<News> news) {


        if (news == null) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.no_news);
        } else {
            newsAdapter = new NewsAdapter(this, R.layout.list_model, news);
            listView.setAdapter(newsAdapter);

            }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri url = Uri.parse(newsAdapter.getItem(position).getNewsLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {

    }


    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
