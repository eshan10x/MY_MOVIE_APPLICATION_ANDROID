package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditMovieActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ListView movieList;
    Button updateBtn;
    ArrayAdapter<MovieData> arrayAdapter;
    ArrayList<MovieData> listContent = new ArrayList<>();
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        setTitle("My Movies");

        movieList = findViewById(R.id.ratingMovieList);
        dbHelper = new DBHelper(this);

        populateView();
        CustomAdapter Customadapter = new CustomAdapter(this, R.layout.activity_edit_movie, listContent);
//        arrayAdapter = new ArrayAdapter<MovieData>(EditMovieActivity.this, android.R.layout.simple_list_item_1, listContent);
        movieList.setAdapter(Customadapter);
    }

    private void populateView() {
        Cursor data = dbHelper.getContent();
        while(data.moveToNext()) {

            int movieKey = data.getInt(0);
            String movieTitle = data.getString(1);
            int movieYear = data.getInt(2);
            String movieDirector = data.getString(3);
            String movieActresses = data.getString(4);
            int movieRatings = data.getInt(5);
            String movieDescription = data.getString(6);
            int movieIsFavourite = data.getInt(7);

            listContent.add(new MovieData(movieKey,movieTitle,movieYear,movieDirector,movieActresses,movieRatings,movieDescription,movieIsFavourite));
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
            }else {
                holder = (ViewHolder) row.getTag();
            }

            final MovieData movieData = content.get(position);
            holder.movieTitle.setText(movieData.getMovieTitle());

            holder.movieTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EditMovieActivity.this, UpdateViewActivity.class);
                    intent.putExtra("movieData", String.valueOf(movieData));
                    intent.putExtra("key",movieData.getMovieKey());
                    intent.putExtra("title",movieData.getMovieTitle());
                    intent.putExtra("year",movieData.getYear());
                    intent.putExtra("director",movieData.getDirector());
                    intent.putExtra("actresses",movieData.getActresses());
                    intent.putExtra("ratings",movieData.getRatings());
                    intent.putExtra("description",movieData.getDescription());
                    intent.putExtra("isFavourite",movieData.getIsFavourite());
                    startActivity(intent);
                    Log.d(TAG, "--------checkList---------" + getItemId(position));
                    Log.d(TAG, "--------moviedata---------" + movieData);

                }
            });

            return row;
        }
    }

}