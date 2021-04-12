package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DisplayMovieActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ListView movieList;
    CheckBox checkBox;
    Button addToFavouriteBtn;
    ArrayList<MovieData> listContent = new ArrayList<>();
    ArrayList<MovieData> checkedProducts = new ArrayList<>();
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        setTitle("My Movies");

        movieList=(ListView)findViewById(R.id.movieList);
        checkBox=(CheckBox)findViewById(R.id.checkboxMovie);
        addToFavouriteBtn = findViewById(R.id.addToFavouriteBTn);
        dbHelper = new DBHelper(this);

        populateView();
        CustomAdapter adapter = new CustomAdapter(this, R.layout.activity_display_movie, listContent);
        movieList.setAdapter(adapter);

        addToFavouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "--------checkList---------" + checkedProducts);
                if(checkedProducts!=null) {
                    for (int i = 0; i < checkedProducts.size(); i++)
                    {
                        dbHelper.updateProduct(checkedProducts.get(i));
                        Toast.makeText(DisplayMovieActivity.this,"Successfully Added to Favourites" ,Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------meka---------" + checkedProducts);
                    }
                    listContent = (ArrayList<MovieData>) dbHelper.getAllData();
                    Log.d(TAG, "--------listContent---------" + listContent);
                }else {
                    Toast.makeText(DisplayMovieActivity.this,"Please select items you want to Add" ,Toast.LENGTH_SHORT).show();
                }
            }
        });

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
            CheckBox checkBox;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.movie_list, null);
                holder = new ViewHolder();
                holder.movieTitle = (TextView) row.findViewById(R.id.titleTxt);
                holder.checkBox = (CheckBox) row.findViewById(R.id.checkboxMovie);

                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.d(TAG, "--------111111111111111111---------" + content);
                        if (isChecked) {
                            content.get(position).setIsFavourite(1);
                        }
                        if (!isChecked) {
                            content.get(position).setIsFavourite(0);
                        }
                        checkedProducts = content;
                        Log.d(TAG, "--------checked Products down---------" + content);
                    }
                });

                row.setTag(holder);
            }else {
                holder = (ViewHolder) row.getTag();
            }

            final MovieData movieData = content.get(position);
            holder.movieTitle.setText(movieData.getMovieTitle());

            return row;
        }
    }

    }