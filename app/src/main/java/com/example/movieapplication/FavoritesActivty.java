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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivty extends AppCompatActivity {

    DBHelper dbHelper;
    ListView movieList;
    CheckBox checkBox;
    Button saveBtn;
    ArrayList<MovieData> checkedProducts = new ArrayList<>();
    ArrayList<MovieData> listContent = new ArrayList<>();
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_activty);

        movieList=(ListView)findViewById(R.id.favMovieList);
        checkBox=(CheckBox)findViewById(R.id.favCheckBox);
        saveBtn = findViewById(R.id.saveBtn);
        dbHelper = new DBHelper(this);

        populateView();
        FavoritesActivty.CustomAdapter adapter = new CustomAdapter(this, R.layout.activity_favorites_activty, checkedProducts);
        movieList.setAdapter(adapter);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkedProducts!=null) {
                    for (int i = 0; i < checkedProducts.size(); i++)
                    {
                        dbHelper.updateProduct(checkedProducts.get(i));
                        Toast.makeText(FavoritesActivty.this,"Successfully removed from Favourites" ,Toast.LENGTH_SHORT).show();
                    }
                    checkedProducts = (ArrayList<MovieData>) dbHelper.getAllData();
                }else {
                    Toast.makeText(FavoritesActivty.this,"Please select items you want to Add" ,Toast.LENGTH_SHORT).show();
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

            if (movieIsFavourite == 1) {
                Log.d(TAG, "--------populate---------" + checkedProducts);
                checkedProducts.add(new MovieData(movieKey,movieTitle,movieYear,movieDirector,movieActresses,movieRatings,movieDescription,movieIsFavourite));
            }else {
                //fav list is empty
            }
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
            TextView favMovieTitle;
            CheckBox favCheckBox;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.favourite_list, null);
                holder = new CustomAdapter.ViewHolder();
                holder.favMovieTitle = (TextView) row.findViewById(R.id.favTitleTxt);
                holder.favCheckBox = (CheckBox) row.findViewById(R.id.favCheckBox);
                holder.favCheckBox.setChecked(true);

                holder.favCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            content.get(position).setIsFavourite(1);
                        }
                        if (!isChecked) {
                            content.get(position).setIsFavourite(0);
                        }
                        checkedProducts = content;
                    }
                });

                row.setTag(holder);
            }else {
                holder = (CustomAdapter.ViewHolder) row.getTag();
            }

            final MovieData movieData = content.get(position);
            holder.favMovieTitle.setText(movieData.getMovieTitle());

            return row;
        }
    }
}