package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InstructionsActivity extends AppCompatActivity {

    TextView register,display,favorite,editMovie,search,ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        register = findViewById(R.id.RegisterInstruction);
        display = findViewById(R.id.Display);
        favorite = findViewById(R.id.Fav);
        editMovie = findViewById(R.id.Edit);
        search = findViewById(R.id.Search);
        ratings = findViewById(R.id.Ratings);

        register.setText(R.string.reister_ins);
        display.setText(R.string.display_ins);
        favorite.setText(R.string.favourite_ins);
        editMovie.setText(R.string.edit_ins);
        search.setText(R.string.search_ins);
        ratings.setText(R.string.ratings_ins);

    }
}