package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("My Movies");
    }

    public void regMovie(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterMovieActivity.class);
        startActivity(intent);
    }

    public void displayMovieScreen(View view) {
        Intent intent = new Intent(MainActivity.this, DisplayMovieActivity.class);
        startActivity(intent);
    }

    public void favoritesScreen(View view) {
        Intent intent = new Intent(MainActivity.this, FavoritesActivty.class);
        startActivity(intent);
    }

    public void editMovieScreen(View view) {
        Intent intent = new Intent(MainActivity.this, EditMovieActivity.class);
        startActivity(intent);
    }

    public void searchRateScreen(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void rateScreen(View view) {
        Intent intent = new Intent(MainActivity.this, RatingsActivity.class);
        startActivity(intent);
    }
}