package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class RatingsActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    ArrayList<MovieData> listContent = new ArrayList<>();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        setTitle("My Movies");

        listView = findViewById(R.id.ratingMovieList);
        dbHelper = new DBHelper(this);

        populateView();
        CustomAdapter Customadapter = new CustomAdapter(this, R.layout.activity_ratings, listContent);
//        arrayAdapter = new ArrayAdapter<MovieData>(EditMovieActivity.this, android.R.layout.simple_list_item_1, listContent);
        listView.setAdapter(Customadapter);

    }

    private void populateView() {
        Cursor data = dbHelper.getContent();
        while (data.moveToNext()) {

            int movieKey = data.getInt(0);
            String movieTitle = data.getString(1);
            int movieYear = data.getInt(2);
            String movieDirector = data.getString(3);
            String movieActresses = data.getString(4);
            int movieRatings = data.getInt(5);
            String movieDescription = data.getString(6);
            int movieIsFavourite = data.getInt(7);

            listContent.add(new MovieData(movieKey, movieTitle, movieYear, movieDirector, movieActresses, movieRatings, movieDescription, movieIsFavourite));
        }
    }

    public class CustomAdapter extends BaseAdapter {

        private Context context;
        private int layout;
        ArrayList<MovieData> content;

        public CustomAdapter(Context context, int layout, ArrayList<MovieData> content) {
            this.context = context;
            this.layout = layout;
            this.content = content;
        }

        @Override
        public int getCount() {
            return content.size();
        }

        @Override
        public Object getItem(int position) {
            return content.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {

            TextView movieTitle;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.edit_list, null);
                holder = new ViewHolder();
                holder.movieTitle = (TextView) row.findViewById(R.id.editTitleTxt);

                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            final MovieData movieData = content.get(position);
            holder.movieTitle.setText(movieData.getMovieTitle());

            holder.movieTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RatingsActivity.this, RatingsTwoActivity.class);
                    intent.putExtra("movieData", String.valueOf(movieData));
                    intent.putExtra("key", movieData.getMovieKey());
                    intent.putExtra("title", movieData.getMovieTitle());
                    intent.putExtra("year", movieData.getYear());
                    intent.putExtra("director", movieData.getDirector());
                    intent.putExtra("actresses", movieData.getActresses());
                    intent.putExtra("ratings", movieData.getRatings());
                    intent.putExtra("description", movieData.getDescription());
                    intent.putExtra("isFavourite", movieData.getIsFavourite());
                    startActivity(intent);

                }
            });

            return row;
        }
    }
}