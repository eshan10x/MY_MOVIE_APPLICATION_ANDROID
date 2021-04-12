package com.example.movieapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MovieList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] movieName;

    public MovieList(@NonNull Activity context, String[] movieName) {
        super(context, R.layout.movie_list, movieName);
        this.context = context;
        this.movieName = movieName;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.movie_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.titleTxt);

        titleText.setText(movieName[position]);

        return rowView;

    }
}
