package com.example.sara.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncTaskLoaderClass extends AsyncTaskLoader {


     String newsURL;

    ArrayList<News> newsList;

    String title;
    String author;
    String date;
    String link;
    String section;
    String image;

    public AsyncTaskLoaderClass(@NonNull Context context,String Url) {
        super(context);
newsURL=Url;
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        StringBuilder mStringBuilder = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(newsURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();

            while (line != null) {
                mStringBuilder.append(line);
                line = bufferedReader.readLine();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject root = new JSONObject(mStringBuilder.toString());

            JSONObject response = root.getJSONObject("response");
            JSONArray articles = response.getJSONArray("results");


            newsList = new ArrayList<>();


            for (int i = 0; i < articles.length(); i++) {
                JSONObject elements = articles.getJSONObject(i);


                        title = elements.getString("webTitle");
                        date = elements.getString("webPublicationDate");


                        if (elements.has("webUrl")) {
                            link = elements.getString("webUrl");

                        }
                        if (elements.has("sectionName")) {
                            section = elements.getString("sectionName");

                        }
                        if (elements.has("fields")) {
                            JSONObject fields = elements.getJSONObject("fields");
                            if (fields.has("byline")) {
                                author = fields.getString("byline");
                            }
                            if (fields.has("thumbnail")) {
                                image = fields.getString("thumbnail");

                            }
                        }

                        newsList.add(new News(title, author, link, date, section, image));


                    }

                }


        catch (JSONException e) {
            e.printStackTrace();
        }


        return newsList;
    }
}
