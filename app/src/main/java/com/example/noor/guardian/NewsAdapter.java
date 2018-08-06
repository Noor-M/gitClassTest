package com.example.noor.guardian;

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
    Context context;
    int resource;
    ArrayList<News> objects;

    public NewsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<News> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, parent, false);
        }

        TextView author = convertView.findViewById(R.id.author);
        TextView title = convertView.findViewById(R.id.titleName);
        TextView date = convertView.findViewById(R.id.date);
        TextView section = convertView.findViewById(R.id.section);
        ImageView image = convertView.findViewById(R.id.image);


        News news = objects.get(position);

        author.setText(news.getAuthor());
        title.setText(news.getTitle());
        date.setText(news.getDate());
        section.setText(news.getSectionName());
        Picasso.get().load(news.getImage()).into(image);


        return convertView;


    }
}
