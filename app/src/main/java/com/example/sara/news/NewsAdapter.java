package com.example.sara.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    Context mContext;
    int mReasource;
    ArrayList<News> mObjects;

    public NewsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<News> objects) {
        super(context, resource, objects);
        mContext = context;
        mReasource = resource;
        mObjects = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NewsViewHolder newsViewHolder = new NewsViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mReasource, parent, false);

            newsViewHolder.titleTextView = convertView.findViewById(R.id.title_tv);
            newsViewHolder.sectionTextView = convertView.findViewById(R.id.section_tv);
            newsViewHolder.authorTextView = convertView.findViewById(R.id.auhtor_tv);
            newsViewHolder.dateTextView = convertView.findViewById(R.id.date_tv);
            newsViewHolder.imageView=convertView.findViewById(R.id.image_iv);
            convertView.setTag(newsViewHolder);
        } else {

            newsViewHolder = (NewsViewHolder) convertView.getTag();
        }
        News news = getItem(position);
        newsViewHolder.titleTextView.setText(news.getTitle());
        newsViewHolder.sectionTextView.setText(news.getSection());
        newsViewHolder.authorTextView.setText(news.getAuthor());
        newsViewHolder.dateTextView.setText(news.getDate());
       Picasso.get().load(news.getImage()).into(newsViewHolder.imageView);
        return convertView;
    }


}

class NewsViewHolder {
    TextView titleTextView;
    TextView sectionTextView;
    TextView authorTextView;
    TextView dateTextView;
ImageView imageView;
}