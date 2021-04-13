package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText searchName;
    Button searchBtn;
    DBHelper dbHelper;
    ListView searchMovieList;
    List<String> movies;
    ArrayAdapter<String> arrayAdapter;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("My Movies");

        searchName = findViewById(R.id.lookUpTxt);
        searchBtn = findViewById(R.id.lookUpBtn);
        searchMovieList = findViewById(R.id.SearchMovieList);
        dbHelper = new DBHelper(this);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "--------checkList---------" );
                if(searchName.getText()!=null) {
                    if(!searchName.getText().toString().isEmpty()) {
                        movies = dbHelper.getSearchProducts(searchName.getText().toString().toLowerCase());
                        Log.d(TAG, "--------checkList---------"  + movies);
                        if(movies !=null) {
                            if(movies.size()>0) {
                                arrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, movies);
                                searchMovieList.setAdapter(arrayAdapter);
                                return;
                            }
                        }
                        searchMovieList.setAdapter(null);
                        Toast.makeText(SearchActivity.this,"Movie Not Found" ,Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                searchMovieList.setAdapter(null);
                Toast.makeText(SearchActivity.this,"Please enter any text to search" ,Toast.LENGTH_SHORT).show();

            }
        });

    }
}